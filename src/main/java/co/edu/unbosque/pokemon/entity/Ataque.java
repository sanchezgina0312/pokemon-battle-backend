package co.edu.unbosque.pokemon.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Ataque {
	
    private @Id String nombreAtaqueApi;
    private boolean estaBaneado;
    private Integer poderModificado;

    public Ataque() {
		
	}

	public Ataque(String nombreAtaqueApi, boolean estaBaneado, Integer poderModificado) {
		super();
		this.nombreAtaqueApi = nombreAtaqueApi;
		this.estaBaneado = estaBaneado;
		this.poderModificado = poderModificado;
	}

	public String getNombreAtaqueApi() {
		return nombreAtaqueApi;
	}

	public void setNombreAtaqueApi(String nombreAtaqueApi) {
		this.nombreAtaqueApi = nombreAtaqueApi;
	}

	public boolean isEstaBaneado() {
		return estaBaneado;
	}

	public void setEstaBaneado(boolean estaBaneado) {
		this.estaBaneado = estaBaneado;
	}

	public Integer getPoderModificado() {
		return poderModificado;
	}

	public void setPoderModificado(Integer poderModificado) {
		this.poderModificado = poderModificado;
	}

	@Override
	public int hashCode() {
		return Objects.hash(estaBaneado, nombreAtaqueApi, poderModificado);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ataque other = (Ataque) obj;
		return estaBaneado == other.estaBaneado && Objects.equals(nombreAtaqueApi, other.nombreAtaqueApi)
				&& Objects.equals(poderModificado, other.poderModificado);
	}

	@Override
	public String toString() {
		return "Ataque [nombreAtaqueApi=" + nombreAtaqueApi + ", estaBaneado=" + estaBaneado + ", poderModificado="
				+ poderModificado + "]";
	}
    
}