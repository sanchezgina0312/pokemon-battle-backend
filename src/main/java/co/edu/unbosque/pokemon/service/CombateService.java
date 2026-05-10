package co.edu.unbosque.pokemon.service;

import org.springframework.stereotype.Service;
import co.edu.unbosque.pokemon.entity.Pokemon;

@Service
public class CombateService {

	public int procesarTurno(Pokemon atacante, Pokemon defensor, int poderAtaque, double modificador) {
		int atk = atacante.getNivel() * 2; 
		int def = defensor.getNivel() * 2;

		double parte1 = ((2.0 * atacante.getNivel()) / 5.0) + 2.0;
		double parte2 = (parte1 * poderAtaque * ((double) atk / def)) / 50.0;
		
		int danioFinal = (int) ((parte2 + 2.0) * modificador);
		
		defensor.setSaludActual(defensor.getSaludActual() - danioFinal);
		if (defensor.getSaludActual() < 0) defensor.setSaludActual(0);
		
		return danioFinal;
	}
}