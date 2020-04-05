package pl.edu.agh.ki.covid19tablet.schema.dto

import pl.edu.agh.ki.covid19tablet.schema.SchemaId
import pl.edu.agh.ki.covid19tablet.schema.fields.dto.SchemaFieldsDTO
import pl.edu.agh.ki.covid19tablet.schema.signature.dto.SignatureFieldDTO

data class SchemaDTO(
    val id: SchemaId,
    val name: String,

    val fields: SchemaFieldsDTO,
    val multiPage: Boolean,
    val patientSignature: SignatureFieldDTO,
    val employeeSignature: SignatureFieldDTO
)
