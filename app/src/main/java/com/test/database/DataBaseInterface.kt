/**
 * Created by Julian Centurion on 02/02/2022.
 */

package com.test.database

import com.test.dto.User

interface DataBaseInterface {
    fun store(userName: String)
    fun loadUserName(callback: (User) -> Unit)
}
