package com.lockwood.automata.intent

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract

fun Activity.selectContact(
    requestCode: Int,
): Intent {
    return Intent(Intent.ACTION_PICK).apply {
        type = ContactsContract.Contacts.CONTENT_TYPE

        startActivityForResult(intent, requestCode)
    }
}

fun Activity.selectContactByPhone(
    requestCode: Int,
): Intent {
    return Intent(Intent.ACTION_PICK).apply {
        type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE

        startActivityForResult(intent, requestCode)
    }
}

fun Context.viewContact(
    contactUri: Uri,
): Intent {
    return Intent(Intent.ACTION_VIEW, contactUri).apply {
        startActivity(this)
    }
}

fun Context.editContactMail(
    contactUri: Uri,
    email: String,
): Intent {
    return editContact(
        contactUri = contactUri,
        name = ContactsContract.Intents.Insert.EMAIL,
        value = email
    )
}

fun Context.editContact(
    contactUri: Uri,
    name: String,
    value: String,
): Intent {
    return Intent(Intent.ACTION_EDIT, contactUri).apply {
        putExtra(name, value)

        startActivity(this)
    }
}

fun Context.insertContact(
    name: String,
    email: String,
): Intent {
    return Intent(Intent.ACTION_INSERT).apply {
        type = ContactsContract.Contacts.CONTENT_TYPE
        putExtra(ContactsContract.Intents.Insert.NAME, name)
        putExtra(ContactsContract.Intents.Insert.EMAIL, email)

        startActivity(this)
    }
}
