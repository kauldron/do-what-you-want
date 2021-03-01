package com.lockwood.automata.intent

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import com.lockwood.automata.android.buildIntent

fun Activity.selectContact(
    requestCode: Int,
) = buildIntent(Intent.ACTION_PICK) {
    type = ContactsContract.Contacts.CONTENT_TYPE

    startActivityForResult(intent, requestCode)
    return@buildIntent
}

fun Activity.selectContactByPhone(
    requestCode: Int,
) = buildIntent(Intent.ACTION_PICK) {
    type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE

    startActivityForResult(intent, requestCode)
    return@buildIntent
}

fun Context.viewContact(
    contactUri: Uri,
) = buildIntent(Intent.ACTION_VIEW, contactUri) {
    startActivity(this)
    return@buildIntent
}

fun Context.editContactMail(
    contactUri: Uri,
    email: String,
) = editContact(
    contactUri = contactUri,
    name = ContactsContract.Intents.Insert.EMAIL,
    value = email
)

fun Context.editContact(
    contactUri: Uri,
    name: String,
    value: String,
) = buildIntent(Intent.ACTION_EDIT, contactUri) {
    putExtra(name, value)

    startActivity(this)
    return@buildIntent
}

fun Context.insertContact(
    name: String,
    email: String,
) = buildIntent(Intent.ACTION_INSERT) {
    type = ContactsContract.Contacts.CONTENT_TYPE
    putExtra(ContactsContract.Intents.Insert.NAME, name)
    putExtra(ContactsContract.Intents.Insert.EMAIL, email)

    startActivity(this)
    return@buildIntent
}