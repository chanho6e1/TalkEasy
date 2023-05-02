package com.ssafy.talkeasy.core.domain.entity.base

interface DataToDomainMapper<T> {

    fun toDomainModel(): T
}