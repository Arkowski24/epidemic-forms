package pl.edu.agh.ki.covid19tablet

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import pl.edu.agh.ki.covid19tablet.form.Form
import pl.edu.agh.ki.covid19tablet.form.FormRepository
import pl.edu.agh.ki.covid19tablet.form.FormStatus
import pl.edu.agh.ki.covid19tablet.form.signature.Signature
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
import pl.edu.agh.ki.covid19tablet.schema.signature.SignatureField
import pl.edu.agh.ki.covid19tablet.state.FormState
import pl.edu.agh.ki.covid19tablet.state.fields.ChoiceFieldState
import pl.edu.agh.ki.covid19tablet.state.fields.DerivedFieldState
import pl.edu.agh.ki.covid19tablet.state.fields.SliderFieldState
import pl.edu.agh.ki.covid19tablet.state.fields.TextFieldState
import pl.edu.agh.ki.covid19tablet.user.employee.Employee
import pl.edu.agh.ki.covid19tablet.user.employee.EmployeeRepository
import pl.edu.agh.ki.covid19tablet.user.patient.Patient
import java.util.*

@Component
@Profile("!prod")
class DatabaseInitializer {

    @Bean
    fun initializeDatabase(
        schemaRepository: SchemaRepository,
        employeeRepository: EmployeeRepository,
        formRepository: FormRepository
    ) =
        CommandLineRunner {
            val user = Employee(
                username = "user",
                fullName = "Jeannette Kalyta",
                passwordHash = "\$2a\$10\$Nm7GiH.CHxvW4eWWUqeldOLhhhv07xhkE/sm6f2XQvVjmnTY7k8Oq"
            )
            val admin = Employee(
                username = "admin",
                fullName = "Dr. Oetker",
                passwordHash = "\$2a\$10\$Nm7GiH.CHxvW4eWWUqeldOLhhhv07xhkE/sm6f2XQvVjmnTY7k8Oq"
            )
            employeeRepository.save(user)
            employeeRepository.save(admin)

            val simpleField1 = SimpleField(
                fieldNumber = 0,
                title = "Dane osobowe",
                fieldType = FieldType.HIDDEN
            )
            val simpleField2 = SimpleField(
                fieldNumber = 4,
                title = "Parametry życiowe",
                fieldType = FieldType.HIDDEN
            )
            val simpleField3 = SimpleField(
                fieldNumber = 10,
                title = "Pytania epidemiczne",
                fieldType = FieldType.HIDDEN
            )
            val choiceField1 = ChoiceField(
                fieldNumber = 8,
                fieldType = FieldType.HIDDEN,
                inline = false,
                title = "Częstość oddechu:",
                choices = listOf("10-20", "20-30", "30-40", ">40")
            )
            val choiceField2 = ChoiceField(
                fieldNumber = 11,
                title = "Czy miał Pan(i) kontakt z osoba zakażoną koronawirusem?",
                choices = listOf("TAK", "NIE")
            )
            val choiceField3 = ChoiceField(
                fieldNumber = 12,
                title = "Czy miał Pan(i) kontakt z osobą w trakcie kwarantanny?",
                choices = listOf("TAK", "NIE")
            )
            val choiceField4 = ChoiceField(
                fieldNumber = 13,
                title = "Czy w ciągu ostatnich dwóch tygodni przebywał Pan(i) za granicą?",
                choices = listOf("TAK", "NIE")
            )
            val choiceField5 = ChoiceField(
                fieldNumber = 14,
                title = "Czy w ciągu ostatnich dwóch tygodni miał Pan(i) jakikolwiek kontakt z osobą, która powróciła z innego kraju?",
                choices = listOf("TAK", "NIE")
            )
            val choiceField6 = ChoiceField(
                fieldNumber = 15,
                title = "Czy miał Pan(i) kontakt z osobą z objawami infekcji dróg oddechowych?",
                choices = listOf("TAK", "NIE")
            )
            val choiceField7 = ChoiceField(
                fieldNumber = 16,
                title = "Czy pracuje Pan(i) w szpitalu lub przychodni lub zakładzie opiekuńczym?",
                choices = listOf("TAK", "NIE")
            )
            val choiceField8 = ChoiceField(
                fieldNumber = 17,
                title = "Czy ma Pan(i) podwyższona temperaturę?",
                choices = listOf("TAK", "NIE")
            )
            val choiceField9 = ChoiceField(
                fieldNumber = 18,
                title = "Czy ma Pan(i) kaszel?",
                choices = listOf("TAK", "NIE")
            )
            val choiceField10 = ChoiceField(
                fieldNumber = 19,
                title = "Czy odczuwa Pan(i) duszność, brak tchu?",
                choices = listOf("TAK", "NIE")
            )
            val derivedField1 = DerivedField(
                fieldNumber = 3,
                derivedType = DerivedType.BIRTHDAY_PESEL,
                titles = listOf("PESEL", "Data urodzin"),
                descriptions = listOf("PESEL pacjenta: ", "Data urodzin pacjenta: ")
            )
            val sliderField1 = SliderField(
                fieldNumber = 5,
                fieldType = FieldType.HIDDEN,
                inline = false,
                title = "Temperatura:",
                minValue = 34.0,
                maxValue = 43.0,
                defaultValue = 36.6,
                step = 0.1
            )
            val sliderField2 = SliderField(
                fieldNumber = 6,
                fieldType = FieldType.HIDDEN,
                inline = false,
                title = "Ciśnienie skurczowe:",
                minValue = 50.0,
                maxValue = 300.0,
                defaultValue = 120.0,
                step = 10.0
            )
            val sliderField3 = SliderField(
                fieldNumber = 7,
                fieldType = FieldType.HIDDEN,
                inline = false,
                title = "Tętno:",
                minValue = 20.0,
                maxValue = 200.0,
                defaultValue = 70.0,
                step = 10.0
            )
            val sliderField4 = SliderField(
                fieldNumber = 9,
                fieldType = FieldType.HIDDEN,
                inline = false,
                title = "Saturacja:",
                minValue = 80.0,
                maxValue = 100.0,
                defaultValue = 95.0,
                step = 5.0
            )
            val textField1 = TextField(
                fieldNumber = 1,
                title = "Nazwisko:"
            )
            val textField2 = TextField(
                fieldNumber = 2,
                title = "Imiona:"
            )
            val textField3 = TextField(
                fieldNumber = 20,
                fieldType = FieldType.HIDDEN,
                inline = false,
                title = "Dodatkowe objawy:",
                multiLine = true
            )

            val schema = schemaRepository.save(
                Schema(
                    name = "Formularz epidemiczny",
                    multiPage = false,
                    fields = SchemaFields(
                        simple = listOf(
                            simpleField1,
                            simpleField2,
                            simpleField3
                        ),
                        choice = listOf(
                            choiceField1,
                            choiceField2,
                            choiceField3,
                            choiceField4,
                            choiceField5,
                            choiceField6,
                            choiceField7,
                            choiceField8,
                            choiceField9,
                            choiceField10
                        ),
                        derived = listOf(
                            derivedField1
                        ),
                        slider = listOf(
                            sliderField1,
                            sliderField2,
                            sliderField3,
                            sliderField4
                        ),
                        text = listOf(
                            textField1,
                            textField2,
                            textField3
                        )
                    ),
                    patientSignature = SignatureField(
                        title = "Podpis - Pacjent",
                        description = "Oświadczenie: mam świadomość odpowiedzialności karnej w przypadku podania nieprawdziwych informacji."
                    ),
                    employeeSignature = SignatureField(
                        title = "Podpis - Pracownik",
                        description = "Oświadczenie: potwierdzam prawidłowość wprowadzonych informacji."
                    )
                )
            )

            formRepository.save(
                Form(
                    schema = schema,
                    formName = "Formularz Epidemiczny",
                    status = FormStatus.CLOSED,
                    state = FormState(
                        choice = listOf(
                            ChoiceFieldState(
                                field = choiceField1,
                                value = listOf(false, false, true, false)
                            ),
                            ChoiceFieldState(
                                field = choiceField2,
                                value = listOf(true, false)
                            ),
                            ChoiceFieldState(
                                field = choiceField3,
                                value = listOf(true, false)
                            ),
                            ChoiceFieldState(
                                field = choiceField4,
                                value = listOf(true, false)
                            ),
                            ChoiceFieldState(
                                field = choiceField5,
                                value = listOf(true, false)
                            ),
                            ChoiceFieldState(
                                field = choiceField6,
                                value = listOf(true, false)
                            ),
                            ChoiceFieldState(
                                field = choiceField7,
                                value = listOf(true, false)
                            ),
                            ChoiceFieldState(
                                field = choiceField8,
                                value = listOf(true, false)
                            ),
                            ChoiceFieldState(
                                field = choiceField9,
                                value = listOf(true, false)
                            ),
                            ChoiceFieldState(
                                field = choiceField10,
                                value = listOf(true, false)
                            )
                        ),
                        derived = listOf(
                            DerivedFieldState(
                                field = derivedField1,
                                value = listOf(
                                    "89121212345",
                                    "12-12-1989"
                                )
                            )
                        ),
                        slider = listOf(
                            SliderFieldState(
                                field = sliderField1,
                                value = 40.5
                            ),
                            SliderFieldState(
                                field = sliderField2,
                                value = 200.0
                            ),
                            SliderFieldState(
                                field = sliderField3,
                                value = 120.0
                            ),
                            SliderFieldState(
                                field = sliderField4,
                                value = 100.0
                            )
                        ),
                        text = listOf(
                            TextFieldState(
                                field = textField1,
                                value = "Kowalski"
                            ),
                            TextFieldState(
                                field = textField2,
                                value = "Jan"
                            ),
                            TextFieldState(
                                field = textField3,
                                value = "Chyba ma wysypkę na pięcie."
                            )
                        )
                    ),
                    createdBy = user,
                    patient = Patient(
                        loggedIn = true
                    ),
                    patientSignature = Signature(
                        value = Base64.getDecoder().decode("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")
                    ),
                    employeeSignature = Signature(
                        value = Base64.getDecoder().decode("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")
                    )
                )
            )
        }
}
