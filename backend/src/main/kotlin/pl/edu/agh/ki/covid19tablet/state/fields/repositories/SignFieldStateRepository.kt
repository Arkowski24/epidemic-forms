package pl.edu.agh.ki.covid19tablet.state.fields.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pl.edu.agh.ki.covid19tablet.state.fields.SignFieldState
import pl.edu.agh.ki.covid19tablet.state.fields.SignFieldStateId

@Repository
interface SignFieldStateRepository : CrudRepository<SignFieldState, SignFieldStateId>
