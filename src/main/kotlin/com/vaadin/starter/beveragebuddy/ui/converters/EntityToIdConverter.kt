package com.vaadin.starter.beveragebuddy.ui.converters

import com.github.vokorm.Entity
import com.github.vokorm.db
import com.github.vokorm.findById
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.data.converter.Converter
import com.vaadin.flow.data.binder.Result
import com.vaadin.flow.data.binder.ValueContext
import com.vaadin.starter.beveragebuddy.LEntity

class EntityToIdConverter2<T: Entity<Long>>(val clazz: Class<T>) : Converter<T?, Long?> {
    override fun convertToModel(value: T?, context: ValueContext?): Result<Long?> =
        Result.ok(value?.id)

    override fun convertToPresentation(value: Long?, context: ValueContext?): T? {
        if (value == null) return null
        return db { con.findById(clazz, value) }
    }
}

@JvmName("toIdEntity")
inline fun <BEAN, reified ENTITY: Entity<Long>> Binder.BindingBuilder<BEAN, ENTITY?>.toId(): Binder.BindingBuilder<BEAN, Long?> =
    withConverter(EntityToIdConverter2(ENTITY::class.java))
