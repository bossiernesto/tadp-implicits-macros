package org.uqbar.tadep.macros

object E01_CaseClasses {
	class Alumno
	type Curso = List[Alumno]

	//═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════
	// Con Magia
	//═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════

	object ConMagia {

		// Properties de sólo lectura
		// Copy
		// Conversión a String lindo
		// Comparación / hashing
		// Constructor sin new, parcialmente aplicable
		// Unapply
		// Etc...
		case class Materia(nombre: String, ciclo: Int)(criterioDeAprobación: Alumno => Boolean) {
			def aprobados(curso: Curso) = curso filter criterioDeAprobación
		}
	}

	//═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════
	// Sin Magia
	//═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════

	object SinMagia {

		class Materia {
			// Properties de sólo lectura
			private var _nombre: String = _
			def nombre = _nombre
			private var _ciclo: Int = _
			def ciclo = _ciclo
			private var _criterioDeAprobación: Alumno => Boolean = _
			private def criterioDeAprobación = _criterioDeAprobación

			private def this(nombre: String, ciclo: Int, criterioDeAprovación: Alumno => Boolean) {
				this()
				_nombre = nombre
				_ciclo = ciclo
				_criterioDeAprobación = criterioDeAprobación
			}

			// Copy
			def copy(nombre: String = this.nombre, ciclo: Int = this.ciclo) = new Materia(nombre,ciclo,criterioDeAprobación)
			
			// Conversión a String lindo
			override def toString = s"Materia($nombre, $ciclo)"

			// Comparación / hashing
			override def equals(other: Any) = other match {
				case m: Materia => m.nombre == nombre && m.ciclo == ciclo
				case _ => false
			}
			override def hashCode = ???

			def aprobados(curso: Curso) = curso filter criterioDeAprobación
		}

		object Materia {
			// Constructor sin new, parcialmente aplicable
			def apply(nombre: String, ciclo: Int) = { criterioDeAprobación: (Alumno => Boolean) =>
				new Materia(nombre, ciclo, criterioDeAprobación)
			}

			// Unapply
			def unapply(materia: Materia): Option[(String, Int)] = ???
		}
	}
}