package pl.edu.agh.ki.covid19tablet.form.signature

import org.springframework.data.repository.CrudRepository

interface SignatureRepository : CrudRepository<Signature, SignatureId>
