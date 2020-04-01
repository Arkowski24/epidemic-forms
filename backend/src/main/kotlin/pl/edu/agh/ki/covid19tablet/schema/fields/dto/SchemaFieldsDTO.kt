package pl.edu.agh.ki.covid19tablet.schema.fields.dto

data class SchemaFieldsDTO(
    val choice: List<ChoiceFieldDTO>,
    val sign: List<SignFieldDTO>,
    val simple: List<SimpleFieldDTO>,
    val slider: List<SliderFieldDTO>,
    val text: List<TextFieldDTO>
)
