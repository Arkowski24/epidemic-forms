package pl.edu.agh.ki.covid19tablet.state.fields.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pl.edu.agh.ki.covid19tablet.state.fields.DerivedFieldState
import pl.edu.agh.ki.covid19tablet.state.fields.DerivedFieldStateId

@Repository
interface DerivedFieldStateRepository : CrudRepository<DerivedFieldState, DerivedFieldStateId>
