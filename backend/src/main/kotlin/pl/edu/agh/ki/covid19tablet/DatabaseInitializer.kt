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
import pl.edu.agh.ki.covid19tablet.schema.fields.SimpleField
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
                                    fieldNumber = 0,
                                    fieldType = FieldType.HIDDEN,
                                    title = "Cel wizyty",
                                    choices = listOf("Pacjent", "Odwiedziny", "Pracownik")
                                ),
                                ChoiceField(
                                    fieldNumber = 8,
                                    fieldType = FieldType.HIDDEN,
                                    title = "Częstość oddechu",
                                    choices = listOf("B.D.", "10-20", "20-30", "30-40", ">40")
                                ),
                                ChoiceField(
                                    fieldNumber = 9,
                                    title = "Czy miał Pan(i) kontakt z osobą zakażoną koronawirusem?",
                                    choices = listOf("TAK", "NIE"),
                                    required = true
                                ),
                                ChoiceField(
                                    fieldNumber = 10,
                                    title = "Czy miał Pan(i) kontakt z osobą w trakcie kwarantanny?",
                                    choices = listOf("TAK", "NIE"),
                                    required = true
                                ),
                                ChoiceField(
                                    fieldNumber = 11,
                                    title = "Czy w ciągu ostatnich dwóch tygodni przebywał Pan(i) za granicą?",
                                    choices = listOf("TAK", "NIE"),
                                    required = true
                                ),
                                ChoiceField(
                                    fieldNumber = 12,
                                    title = "Czy w ciągu ostatnich dwóch tygodni miał Pan(i) jakikolwiek kontakt z osobą, która powróciła z innego kraju?",
                                    choices = listOf("TAK", "NIE"),
                                    required = true
                                ),
                                ChoiceField(
                                    fieldNumber = 13,
                                    title = "Czy miał Pan(i) kontakt z osobą z objawami infekcji dróg oddechowych?",
                                    choices = listOf("TAK", "NIE"),
                                    required = true
                                ),
                                ChoiceField(
                                    fieldNumber = 14,
                                    title = "Czy pracuje Pan(i) w szpitalu lub przychodni, lub zakładzie opiekuńczym?",
                                    choices = listOf("TAK", "NIE"),
                                    required = true
                                ),
                                ChoiceField(
                                    fieldNumber = 15,
                                    title = "Czy ma Pan(i) podwyższoną temperaturę?",
                                    choices = listOf("TAK", "NIE"),
                                    required = true
                                ),
                                ChoiceField(
                                    fieldNumber = 16,
                                    title = "Czy ma Pan(i) kaszel?",
                                    choices = listOf("TAK", "NIE"),
                                    required = true
                                ),
                                ChoiceField(
                                    fieldNumber = 17,
                                    title = "Czy odczuwa Pan(i) duszność, brak tchu?",
                                    choices = listOf("TAK", "NIE"),
                                    required = true
                                )
                            ),
                            derived = listOf(
                                DerivedField(
                                    fieldNumber = 3,
                                    derivedType = DerivedType.BIRTHDAY_PESEL,
                                    titles = listOf("PESEL", "Data urodzenia"),
                                    required = listOf(true, false)
                                )
                            ),
                            slider = listOf(
                                SliderField(
                                    fieldNumber = 4,
                                    fieldType = FieldType.HIDDEN,
                                    inline = false,
                                    title = "Temperatura",
                                    minValue = 34.0,
                                    maxValue = 43.0,
                                    defaultValue = 36.6,
                                    step = 0.1
                                ),
                                SliderField(
                                    fieldNumber = 5,
                                    fieldType = FieldType.HIDDEN,
                                    inline = false,
                                    title = "Ciśnienie skurczowe",
                                    minValue = 50.0,
                                    maxValue = 300.0,
                                    defaultValue = 120.0,
                                    step = 10.0
                                ),
                                SliderField(
                                    fieldNumber = 6,
                                    fieldType = FieldType.HIDDEN,
                                    inline = false,
                                    title = "Tętno",
                                    minValue = 20.0,
                                    maxValue = 200.0,
                                    defaultValue = 70.0,
                                    step = 10.0
                                ),
                                SliderField(
                                    fieldNumber = 7,
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
                                    fieldNumber = 1,
                                    title = "Nazwisko",
                                    required = true
                                ),
                                TextField(
                                    fieldNumber = 2,
                                    title = "Imię",
                                    required = true
                                ),
                                TextField(
                                    fieldNumber = 18,
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

                schemaRepository.save(
                    Schema(
                        name = "Formularz epidemiczny 2",
                        multiPage = false,
                        fields = SchemaFields(
                            simple = listOf(
                                SimpleField(
                                    fieldNumber = 4,
                                    title = "Adres zamieszkania lub pobytu"
                                ),
                                SimpleField(
                                    fieldNumber = 13,
                                    title = "Czy w ciągu ostatnich 24 godzin występowały u Pani/Pana poniższe objawy?"
                                )
                            ),
                            choice = listOf(
                                ChoiceField(
                                    fieldNumber = 0,
                                    fieldType = FieldType.HIDDEN,
                                    title = "Cel wizyty",
                                    choices = listOf("Pacjent", "Odwiedziny", "Pracownik")
                                ),
                                ChoiceField(
                                    fieldNumber = 7,
                                    fieldType = FieldType.HIDDEN,
                                    title = "Częstość oddechu",
                                    choices = listOf("B.D.", "10-20", "20-30", "30-40", ">40")
                                ),
                                ChoiceField(
                                    fieldNumber = 14,
                                    title = "Gorączka (powyżej 38 °C)",
                                    choices = listOf("TAK", "NIE"),
                                    required = true
                                ),
                                ChoiceField(
                                    fieldNumber = 15,
                                    title = "Kaszel",
                                    choices = listOf("TAK", "NIE"),
                                    required = true
                                ),
                                ChoiceField(
                                    fieldNumber = 16,
                                    title = "Bóle mięśniowe",
                                    choices = listOf("TAK", "NIE"),
                                    required = true
                                ),
                                ChoiceField(
                                    fieldNumber = 17,
                                    title = "Dreszcze",
                                    choices = listOf("TAK", "NIE"),
                                    required = true
                                ),
                                ChoiceField(
                                    fieldNumber = 18,
                                    title = "Duszność",
                                    choices = listOf("TAK", "NIE"),
                                    required = true
                                )
                            ),
                            derived = listOf(
                                DerivedField(
                                    fieldNumber = 3,
                                    derivedType = DerivedType.BIRTHDAY_PESEL,
                                    titles = listOf("PESEL", "Data urodzenia"),
                                    required = listOf(true, false)
                                ),
                                DerivedField(
                                    fieldNumber = 5,
                                    derivedType = DerivedType.ADDRESS,
                                    titles = listOf("Ulica", "Miejscowość"),
                                    required = listOf(true, true)
                                ),
                                DerivedField(
                                    fieldNumber = 11,
                                    derivedType = DerivedType.CHOICE_INFO,
                                    titles = listOf(
                                        "Czy w ciągu ostatnich 14 dni przebywał(a) Pan(i) za granicą?",
                                        "W których krajach?"
                                    ),
                                    required = listOf(true, true)
                                ),
                                DerivedField(
                                    fieldNumber = 12,
                                    derivedType = DerivedType.CHOICE_INFO,
                                    titles = listOf(
                                        "Czy miał(a) Pan(i) kontakt z osobą, u której potwierdzono zakażenie koronawirusem?",
                                        "Kiedy?",
                                        "Z kim?"
                                    ),
                                    required = listOf(true, true, true)
                                )
                            ),
                            slider = listOf(
                                SliderField(
                                    fieldNumber = 8,
                                    fieldType = FieldType.HIDDEN,
                                    inline = false,
                                    title = "Temperatura",
                                    minValue = 34.0,
                                    maxValue = 43.0,
                                    defaultValue = 36.6,
                                    step = 0.1
                                ),
                                SliderField(
                                    fieldNumber = 9,
                                    fieldType = FieldType.HIDDEN,
                                    inline = false,
                                    title = "Tętno",
                                    minValue = 20.0,
                                    maxValue = 200.0,
                                    defaultValue = 70.0,
                                    step = 10.0
                                ),
                                SliderField(
                                    fieldNumber = 10,
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
                                    fieldNumber = 1,
                                    title = "Nazwisko",
                                    required = true
                                ),
                                TextField(
                                    fieldNumber = 2,
                                    title = "Imię",
                                    required = true
                                ),
                                TextField(
                                    fieldNumber = 6,
                                    title = "Telefon kontaktowy",
                                    required = true
                                ),
                                TextField(
                                    fieldNumber = 19,
                                    fieldType = FieldType.HIDDEN,
                                    inline = false,
                                    title = "Dodatkowe objawy i informacje",
                                    multiLine = true
                                )
                            )
                        ),
                        patientSignature = SignatureField(
                            title = "Podpis - Pacjent",
                            description = "Oświadczam, że podane przeze mnie informacje są prawdziwe.\n" +
                                    "Wyrażam zgodę na przekazanie wyniku badania drogą telefoniczną lub elektroniczną."
                        ),
                        employeeSignature = SignatureField(
                            title = "Podpis - Pracownik",
                            description = "Oświadczenie: potwierdzam prawidłowość wprowadzonych informacji."
                        )
                    )
                )

                schemaRepository.save(
                    Schema(
                        name = "Formularz epidemiczny 2b",
                        multiPage = false,
                        fields = SchemaFields(
                            simple = listOf(
                                SimpleField(
                                    fieldNumber = 4,
                                    title = "Adres zamieszkania lub pobytu"
                                )
                            ),
                            choice = listOf(
                                ChoiceField(
                                    fieldNumber = 0,
                                    fieldType = FieldType.HIDDEN,
                                    title = "Cel wizyty",
                                    choices = listOf(
                                        "Pacjent",
                                        "Odwiedziny",
                                        "Pracownik",
                                        "Rodzina",
                                        "Administracja",
                                        "Inne"
                                    )
                                ),
                                ChoiceField(
                                    fieldNumber = 7,
                                    fieldType = FieldType.HIDDEN,
                                    title = "Częstość oddechu",
                                    choices = listOf("B.D.", "10-20", "20-30", "30-40", ">40")
                                ),
                                ChoiceField(
                                    fieldNumber = 13,
                                    title = "Czy w ciągu ostatnich 24 godzin występowały u Pani/Pana poniższe objawy?",
                                    choices = listOf(
                                        "Gorączka (powyżej 38 °C)",
                                        "Kaszel",
                                        "Bóle mięśniowe",
                                        "Dreszcze",
                                        "Duszność"
                                    ),
                                    multiChoice = true,
                                    required = false
                                )
                            ),
                            derived = listOf(
                                DerivedField(
                                    fieldNumber = 3,
                                    derivedType = DerivedType.BIRTHDAY_PESEL,
                                    titles = listOf("PESEL", "Data urodzenia"),
                                    required = listOf(true, false)
                                ),
                                DerivedField(
                                    fieldNumber = 5,
                                    derivedType = DerivedType.ADDRESS,
                                    titles = listOf("Ulica", "Miejscowość"),
                                    required = listOf(true, true)
                                ),
                                DerivedField(
                                    fieldNumber = 11,
                                    derivedType = DerivedType.CHOICE_INFO,
                                    titles = listOf(
                                        "Czy w ciągu ostatnich 14 dni przebywał(a) Pan(i) za granicą?",
                                        "W których krajach?"
                                    ),
                                    required = listOf(true, true)
                                ),
                                DerivedField(
                                    fieldNumber = 12,
                                    derivedType = DerivedType.CHOICE_INFO,
                                    titles = listOf(
                                        "Czy miał(a) Pan(i) kontakt z osobą, u której potwierdzono zakażenie koronawirusem?",
                                        "Kiedy?",
                                        "Z kim?"
                                    ),
                                    required = listOf(true, true, true)
                                )
                            ),
                            slider = listOf(
                                SliderField(
                                    fieldNumber = 8,
                                    fieldType = FieldType.HIDDEN,
                                    inline = false,
                                    title = "Temperatura",
                                    minValue = 34.0,
                                    maxValue = 43.0,
                                    defaultValue = 36.6,
                                    step = 0.1
                                ),
                                SliderField(
                                    fieldNumber = 9,
                                    fieldType = FieldType.HIDDEN,
                                    inline = false,
                                    title = "Tętno",
                                    minValue = 20.0,
                                    maxValue = 200.0,
                                    defaultValue = 70.0,
                                    step = 10.0
                                ),
                                SliderField(
                                    fieldNumber = 10,
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
                                    fieldNumber = 1,
                                    title = "Nazwisko",
                                    required = true
                                ),
                                TextField(
                                    fieldNumber = 2,
                                    title = "Imię",
                                    required = true
                                ),
                                TextField(
                                    fieldNumber = 6,
                                    title = "Telefon kontaktowy",
                                    required = true
                                ),
                                TextField(
                                    fieldNumber = 14,
                                    fieldType = FieldType.HIDDEN,
                                    inline = false,
                                    title = "Dodatkowe objawy i informacje",
                                    multiLine = true
                                )
                            )
                        ),
                        patientSignature = SignatureField(
                            title = "Podpis - Pacjent",
                            description = "Oświadczam, że podane przeze mnie informacje są prawdziwe.\n" +
                                    "Wyrażam zgodę na przekazanie wyniku badania drogą telefoniczną lub elektroniczną."
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
