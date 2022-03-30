package com.example.arraylisttoshardpreferencewithgson

import android.text.Editable

class ExampleItem(private var firstName: String?, private var lastName: String?, private var mImageSource: Int) {

    public fun getItemFirstName() : String? { return firstName}
    public fun getItemLastName() : String? { return lastName }
    public fun getImageSource() : Int { return mImageSource}
    public fun changeItemFirstName(firstName : String) {
        this.firstName = firstName}

}