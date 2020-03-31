package pl.edu.agh.ki.covid19tablet.formState.fields.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pl.edu.agh.ki.covid19tablet.formState.fields.ChoiceFieldState
import pl.edu.agh.ki.covid19tablet.formState.fields.ChoiceFieldStateId

@Repository
interface ChoiceFieldStateRepository : CrudRepository<ChoiceFieldState, ChoiceFieldStateId>
