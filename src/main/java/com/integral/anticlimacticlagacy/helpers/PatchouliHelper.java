package com.integral.anticlimacticlagacy.helpers;

import com.integral.anticlimacticlagacy.AnticlimacticLagacy;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import vazkii.patchouli.client.base.PersistentData;
import vazkii.patchouli.client.base.PersistentData.DataHolder.BookData;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.book.BookRegistry;

public class PatchouliHelper {

	protected static Book getBook(ResourceLocation location) {
		return BookRegistry.INSTANCE.books.get(location);
	}

	public static Book getAknowledgment() {
		return PatchouliHelper.getBook(Registry.ITEM.getKey(AnticlimacticLagacy.theAcknowledgment));
	}

	private static void setEntryState(ResourceLocation entryLocation, boolean read) {
		Book theBook = PatchouliHelper.getAknowledgment();
		BookEntry entry = theBook.getContents().entries.get(entryLocation);
		BookData data = PersistentData.data.getBookData(theBook);

		if (data == null || data.viewedEntries == null || entry == null || entry.getId() == null)
			return;

		if (read && !data.viewedEntries.contains(entry.getId().toString())) {
			data.viewedEntries.add(entry.getId().toString());
			entry.markReadStateDirty();
		} else if (!read && data.viewedEntries.contains(entry.getId().toString())) {
			data.viewedEntries.remove(entry.getId().toString());
			entry.markReadStateDirty();
		}
	}

	public static void markEntryUnread(ResourceLocation entryLocation) {
		PatchouliHelper.setEntryState(entryLocation, false);
	}

	public static void markEntryRead(ResourceLocation entryLocation) {
		PatchouliHelper.setEntryState(entryLocation, true);
	}

	public static void markEverythingRead() {
		Book theBook = PatchouliHelper.getAknowledgment();

		for (ResourceLocation location :  theBook.getContents().entries.keySet()) {
			PatchouliHelper.markEntryRead(location);
		}
	}

	public static void markEverythingUnread() {
		Book theBook = PatchouliHelper.getAknowledgment();

		for (ResourceLocation location :  theBook.getContents().entries.keySet()) {
			PatchouliHelper.markEntryUnread(location);
		}
	}



}
