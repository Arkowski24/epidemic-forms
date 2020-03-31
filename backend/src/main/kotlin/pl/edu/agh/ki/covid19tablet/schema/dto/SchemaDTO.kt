package pl.edu.agh.ki.covid19tablet.schema.dto

import pl.edu.agh.ki.covid19tablet.schema.SchemaId
import pl.edu.agh.ki.covid19tablet.schema.fields.dto.SchemaFieldsDTO

data class SchemaDTO(
    val id: SchemaId,
    val name: String,
    val fields: SchemaFieldsDTO
)
