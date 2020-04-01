package pl.edu.agh.ki.covid19tablet.schema

import org.springframework.stereotype.Service
import pl.edu.agh.ki.covid19tablet.SchemaNotFoundException
import pl.edu.agh.ki.covid19tablet.schema.dto.SchemaDTO

interface SchemaService {
    fun getAllSchemas(): List<SchemaDTO>
    fun getSchema(schemaId: SchemaId): SchemaDTO
}

@Service
class SchemaServiceImpl(
    private val schemaRepository: SchemaRepository
) : SchemaService {
    override fun getAllSchemas(): List<SchemaDTO> =
        schemaRepository
            .findAll()
            .map { it.toDTO() }
            .toList()

    override fun getSchema(schemaId: SchemaId): SchemaDTO =
        schemaRepository
            .findById(schemaId)
            .orElseThrow { SchemaNotFoundException() }
            .toDTO()

}
