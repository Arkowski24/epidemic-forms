package pl.edu.agh.ki.covid19tablet

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import pl.edu.agh.ki.covid19tablet.form.Form
import pl.edu.agh.ki.covid19tablet.form.FormRepository
import pl.edu.agh.ki.covid19tablet.form.FormService
import pl.edu.agh.ki.covid19tablet.form.dto.CreateFormRequest
import pl.edu.agh.ki.covid19tablet.schema.Schema
import pl.edu.agh.ki.covid19tablet.schema.SchemaRepository
import pl.edu.agh.ki.covid19tablet.schema.fields.ChoiceField
import pl.edu.agh.ki.covid19tablet.schema.fields.DerivedField
import pl.edu.agh.ki.covid19tablet.schema.fields.DerivedType
import pl.edu.agh.ki.covid19tablet.schema.fields.FieldType
import pl.edu.agh.ki.covid19tablet.schema.fields.SchemaFields
import pl.edu.agh.ki.covid19tablet.schema.fields.SimpleField
import pl.edu.agh.ki.covid19tablet.schema.fields.SliderField
import pl.edu.agh.ki.covid19tablet.schema.fields.TextField
import pl.edu.agh.ki.covid19tablet.schema.fields.*
import pl.edu.agh.ki.covid19tablet.schema.signature.SignatureField
import pl.edu.agh.ki.covid19tablet.user.employee.Employee
import pl.edu.agh.ki.covid19tablet.user.employee.EmployeeRepository
import pl.edu.agh.ki.covid19tablet.state.FormState
import pl.edu.agh.ki.covid19tablet.state.fields.ChoiceFieldState
import pl.edu.agh.ki.covid19tablet.state.fields.SliderFieldState
import pl.edu.agh.ki.covid19tablet.state.fields.TextFieldState
import pl.edu.agh.ki.covid19tablet.user.employee.EmployeeRole
import pl.edu.agh.ki.covid19tablet.user.patient.Patient

@Component
@Profile("!prod")
class DatabaseInitializer {

    @Bean
    fun initializeDatabase(schemaRepository: SchemaRepository, formRepository: FormRepository) =
        CommandLineRunner {

            val choiceField1 = ChoiceField(
                fieldNumber = 1,
                title = "Favourite authors",
                description = "Your favourite author is:",
                choices = listOf("Kafka", "Prost", "Tolkien")
            )
            val choiceField2 = ChoiceField(
                fieldNumber = 2,
                title = "Favourite authors pt.2",
                description = "Your favourite author is:",
                choices = listOf("Kafka", "Prost", "Tolkien"),
                isMultiChoice = true
            )
            val derivedField1 = DerivedField(
                fieldNumber = 3,
                derivedType = DerivedType.BIRTHDAY_PESEL,
                titles = listOf("PESEL", "Birthday"),
                descriptions = listOf("Your PESEL: ", "Your birthay:")
            )
            val sliderField1 = SliderField(
                fieldNumber = 3,
                title = "Fancy slider",
                description = "Please slide freely.",
                minValue = 0.0,
                maxValue = 100.0,
                step = 5.0
            )
            val textField1 = TextField(
                fieldNumber = 4,
                title = "Easy question",
                description = "How tall are you?"
            )
            val textField2 = TextField(
                fieldNumber = 5,
                title = "Hard question",
                description = "What have you eaten today?",
                isMultiline = true
            )

            val schema = schemaRepository.save(
                Schema(
                    name = "My Schema",
                    fields = SchemaFields(
                        simple = listOf(
                            SimpleField(
                                fieldNumber = 0,
                                title = "Read document",
                                description = "This form is used by hospital."
                            )
                        ),
                        choice = listOf(
                            choiceField1,
                            choiceField2
                        ),
                        derived = listOf(
                            derivedField1
                        ),
                        slider = listOf(
                            sliderField1
                        ),
                        text = listOf(
                            textField1,
                            textField2
                        )
                    ),
                    patientSignature = SignatureField(
                        title = "Sign field - Patient",
                        description = "I hereby agree everything is alright."
                    ),
                    employeeSignature = SignatureField(
                        title = "Sign field - Employee",
                        description = "I certify everything is all-right."
                    )
                )
            )

            formRepository.save(
                Form(
                    schema = schema,
                    formName = "Testowy Formularz Numer 1",
                    state = FormState(
                        choice = listOf(
                            ChoiceFieldState(
                                field = choiceField1,
                                value = listOf(
                                    false,
                                    false,
                                    true
                                )
                            ),
                            ChoiceFieldState(
                                field = choiceField2,
                                value = listOf(
                                    true,
                                    false,
                                    true
                                )
                            )
                        ),
                        slider = listOf(
                            SliderFieldState(
                                field = sliderField1,
                                value = 9.3
                            )
                        ),
                        text = listOf(
                            TextFieldState(
                                field = textField1,
                                value = "Odpowiedz krotka"
                            ),
                            TextFieldState(
                                field = textField2,
                                value = "Odpowiedz bardzo, ale to naprawde bardzo dluuugasna"
                            )
                        )
                    ),
                    createdBy = Employee(
                        username = "Janko",
                        passwordHash = "tajnehasloxd",
                        fullName = "Jan Kowalski",
                        role = EmployeeRole.EMPLOYEE
                    ),
                    patient = Patient(
                        loggedIn = true
                    )
                )
            )
        }
}
