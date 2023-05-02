package com.ssafy.talkeasy.core.data.remote.datasource.base

interface DataToDomainMapper<T> {

    fun toDomainModel(): T
}