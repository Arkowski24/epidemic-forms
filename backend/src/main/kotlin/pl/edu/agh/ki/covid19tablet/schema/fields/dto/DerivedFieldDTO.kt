package pl.edu.agh.ki.covid19tablet.schema.fields.dto

import pl.edu.agh.ki.covid19tablet.schema.fields.DerivedFieldId
import pl.edu.agh.ki.covid19tablet.schema.fields.DerivedType
import pl.edu.agh.ki.covid19tablet.schema.fields.FieldType

data class DerivedFieldDTO(
    val id: DerivedFieldId,
    val fieldNumber: Int,
    val fieldType: FieldType,

    val derivedType: DerivedType,
    val titles: List<String>,
    val descriptions: List<String>,
    val inline: Boolean,

    val units: List<String>,

    val required: List<Boolean>
)
