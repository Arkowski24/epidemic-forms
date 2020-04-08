package pl.edu.agh.ki.covid19tablet

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import pl.edu.agh.ki.covid19tablet.device.Device
import pl.edu.agh.ki.covid19tablet.schema.Schema
import pl.edu.agh.ki.covid19tablet.schema.SchemaRepository
import pl.edu.agh.ki.covid19tablet.schema.fields.ChoiceField
import pl.edu.agh.ki.covid19tablet.schema.fields.DerivedField
import pl.edu.agh.ki.covid19tablet.schema.fields.DerivedType
import pl.edu.agh.ki.covid19tablet.schema.fields.FieldType
import pl.edu.agh.ki.covid19tablet.schema.fields.SchemaFields
import pl.edu.agh.ki.covid19tablet.schema.fields.SliderField
import pl.edu.agh.ki.covid19tablet.schema.fields.TextField
import pl.edu.agh.ki.covid19tablet.schema.signature.SignatureField
import pl.edu.agh.ki.covid19tablet.user.employee.Employee
import pl.edu.agh.ki.covid19tablet.user.employee.EmployeeRepository
import pl.edu.agh.ki.covid19tablet.user.employee.EmployeeRole

@Component
@Profile("!prod")
class DatabaseInitializer {

    @Bean
    fun initializeDatabase(
        schemaRepository: SchemaRepository,
        employeeRepository: EmployeeRepository
    ) =
        CommandLineRunner {
            if (schemaRepository.count() == 0L) {
                schemaRepository.save(
                    Schema(
                        name = "Formularz epidemiczny",
                        multiPage = false,
                        fields = SchemaFields(
                            simple = listOf(),
                            choice = listOf(
                                ChoiceField(
                                    fieldNumber = 7,
                                    fieldType = FieldType.HIDDEN,
                                    title = "Częstość oddechu",
                                    choices = listOf("B.D.", "10-20", "20-30", "30-40", ">40")
                                ),
                                ChoiceField(
                                    fieldNumber = 8,
                                    title = "Czy miał Pan(i) kontakt z osobą zakażoną koronawirusem?",
                                    choices = listOf("TAK", "NIE"),
                                    required = true
                                ),
                                ChoiceField(
                                    fieldNumber = 9,
                                    title = "Czy miał Pan(i) kontakt z osobą w trakcie kwarantanny?",
                                    choices = listOf("TAK", "NIE"),
                                    required = true
                                ),
                                ChoiceField(
                                    fieldNumber = 10,
                                    title = "Czy w ciągu ostatnich dwóch tygodni przebywał Pan(i) za granicą?",
                                    choices = listOf("TAK", "NIE"),
                                    required = true
                                ),
                                ChoiceField(
                                    fieldNumber = 11,
                                    title = "Czy w ciągu ostatnich dwóch tygodni miał Pan(i) jakikolwiek kontakt z osobą, która powróciła z innego kraju?",
                                    choices = listOf("TAK", "NIE"),
                                    required = true
                                ),
                                ChoiceField(
                                    fieldNumber = 12,
                                    title = "Czy miał Pan(i) kontakt z osobą z objawami infekcji dróg oddechowych?",
                                    choices = listOf("TAK", "NIE"),
                                    required = true
                                ),
                                ChoiceField(
                                    fieldNumber = 13,
                                    title = "Czy pracuje Pan(i) w szpitalu lub przychodni, lub zakładzie opiekuńczym?",
                                    choices = listOf("TAK", "NIE"),
                                    required = true
                                ),
                                ChoiceField(
                                    fieldNumber = 14,
                                    title = "Czy ma Pan(i) podwyższoną temperaturę?",
                                    choices = listOf("TAK", "NIE"),
                                    required = true
                                ),
                                ChoiceField(
                                    fieldNumber = 15,
                                    title = "Czy ma Pan(i) kaszel?",
                                    choices = listOf("TAK", "NIE"),
                                    required = true
                                ),
                                ChoiceField(
                                    fieldNumber = 16,
                                    title = "Czy odczuwa Pan(i) duszność, brak tchu?",
                                    choices = listOf("TAK", "NIE"),
                                    required = true
                                )
                            ),
                            derived = listOf(
                                DerivedField(
                                    fieldNumber = 2,
                                    derivedType = DerivedType.BIRTHDAY_PESEL,
                                    titles = listOf("PESEL", "Data urodzenia"),
                                    required = listOf(true, false)
                                )
                            ),
                            slider = listOf(
                                SliderField(
                                    fieldNumber = 3,
                                    fieldType = FieldType.HIDDEN,
                                    inline = false,
                                    title = "Temperatura",
                                    minValue = 34.0,
                                    maxValue = 43.0,
                                    defaultValue = 36.6,
                                    step = 0.1
                                ),
                                SliderField(
                                    fieldNumber = 4,
                                    fieldType = FieldType.HIDDEN,
                                    inline = false,
                                    title = "Ciśnienie skurczowe",
                                    minValue = 50.0,
                                    maxValue = 300.0,
                                    defaultValue = 120.0,
                                    step = 10.0
                                ),
                                SliderField(
                                    fieldNumber = 5,
                                    fieldType = FieldType.HIDDEN,
                                    inline = false,
                                    title = "Tętno",
                                    minValue = 20.0,
                                    maxValue = 200.0,
                                    defaultValue = 70.0,
                                    step = 10.0
                                ),
                                SliderField(
                                    fieldNumber = 6,
                                    fieldType = FieldType.HIDDEN,
                                    inline = false,
                                    title = "Saturacja",
                                    minValue = 80.0,
                                    maxValue = 100.0,
                                    defaultValue = 95.0,
                                    step = 1.0
                                )
                            ),
                            text = listOf(
                                TextField(
                                    fieldNumber = 0,
                                    title = "Nazwisko",
                                    required = true
                                ),
                                TextField(
                                    fieldNumber = 1,
                                    title = "Imię",
                                    required = true
                                ),
                                TextField(
                                    fieldNumber = 17,
                                    fieldType = FieldType.HIDDEN,
                                    inline = false,
                                    title = "Dodatkowe objawy i informacje",
                                    multiLine = true
                                )
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
            }
            if (employeeRepository.count() == 0L) {
                employeeRepository.save(
                    Employee(
                        username = "user",
                        fullName = "Jeannette Kalyta",
                        passwordHash = "\$2a\$10\$Nm7GiH.CHxvW4eWWUqeldOLhhhv07xhkE/sm6f2XQvVjmnTY7k8Oq",
                        role = EmployeeRole.EMPLOYEE
                    )
                )
                employeeRepository.save(
                    Employee(
                        username = "admin",
                        fullName = "Dr. Oetker",
                        passwordHash = "\$2a\$10\$Nm7GiH.CHxvW4eWWUqeldOLhhhv07xhkE/sm6f2XQvVjmnTY7k8Oq",
                        role = EmployeeRole.ADMIN
                    )
                )
                employeeRepository.save(
                    Device(
                        username = "device",
                        fullName = "Educatorium",
                        passwordHash = "\$2a\$10\$Nm7GiH.CHxvW4eWWUqeldOLhhhv07xhkE/sm6f2XQvVjmnTY7k8Oq",
                        role = EmployeeRole.DEVICE
                    )
                )
            }
        }
}
