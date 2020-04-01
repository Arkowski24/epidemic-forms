package pl.edu.agh.ki.covid19tablet.state.fields.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pl.edu.agh.ki.covid19tablet.state.fields.ChoiceFieldState
import pl.edu.agh.ki.covid19tablet.state.fields.ChoiceFieldStateId

@Repository
interface ChoiceFieldStateRepository : CrudRepository<ChoiceFieldState, ChoiceFieldStateId>
