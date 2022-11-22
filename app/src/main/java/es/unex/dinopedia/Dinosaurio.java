package es.unex.dinopedia;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "Dinosaurio")
public class Dinosaurio {

	@Ignore
	public static final String ITEM_SEP = System.getProperty("line.separator");

	@SerializedName("id")
	@Expose
	@PrimaryKey(autoGenerate = true)
	private long id;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("diet")
	@Expose
	private String diet;
	@SerializedName("lived_in")
	@Expose
	private String livedIn;
	@SerializedName("type")
	@Expose
	private String type;
	@SerializedName("species")
	@Expose
	private String species;
	@SerializedName("period_name")
	@Expose
	private String periodName;
	@SerializedName("length_meters")
	@Expose
	private String lengthMeters;
	@SerializedName("favorite")
	@Expose
	private String favorite;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	@Ignore
	public Dinosaurio() {
	}

	/**
	 *
	 * @param livedIn
	 * @param species
	 * @param name
	 * @param periodName
	 * @param diet
	 * @param type
	 * @param lengthMeters
	 * @param favorite
	 */
	@Ignore
	public Dinosaurio(String name, String diet, String livedIn, String type, String species, String periodName, String lengthMeters, String favorite) {
		super();
		this.name = name;
		this.diet = diet;
		this.livedIn = livedIn;
		this.type = type;
		this.species = species;
		this.periodName = periodName;
		this.lengthMeters = lengthMeters;
		this.favorite = favorite;
	}

	public Dinosaurio(long id, String name, String diet, String livedIn, String type, String species, String periodName, String lengthMeters, String favorite) {
		super();
		this.id = id;
		this.name = name;
		this.diet = diet;
		this.livedIn = livedIn;
		this.type = type;
		this.species = species;
		this.periodName = periodName;
		this.lengthMeters = lengthMeters;
		this.favorite = favorite;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDiet() {
		return diet;
	}

	public void setDiet(String diet) {
		this.diet = diet;
	}

	public String getLivedIn() {
		return livedIn;
	}

	public void setLivedIn(String livedIn) {
		this.livedIn = livedIn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getPeriodName() {
		return periodName;
	}

	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}

	public String getLengthMeters() {
		return lengthMeters;
	}

	public void setLengthMeters(String lengthMeters) {
		this.lengthMeters = lengthMeters;
	}

	public String getFavorite() { return favorite;	}

	public void setFavorite(String favorite) { this.favorite = favorite; }

	@Override
	public String toString() {
		return "Dinosaurio{" +
				"id=" + id +
				", name='" + name + '\'' +
				", diet='" + diet + '\'' +
				", livedIn='" + livedIn + '\'' +
				", type='" + type + '\'' +
				", species='" + species + '\'' +
				", periodName='" + periodName + '\'' +
				", lengthMeters='" + lengthMeters + '\'' +
				", favorite='" + favorite + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Dinosaurio that = (Dinosaurio) o;
		return id == that.id && Objects.equals(name, that.name) && Objects.equals(diet, that.diet) && Objects.equals(livedIn, that.livedIn) && Objects.equals(type, that.type) && Objects.equals(species, that.species) && Objects.equals(periodName, that.periodName) && Objects.equals(lengthMeters, that.lengthMeters) && Objects.equals(favorite, that.favorite);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, diet, livedIn, type, species, periodName, lengthMeters, favorite);
	}

	public String toLog() {
		return "ID: " + id + ITEM_SEP + "Name:" + name + ITEM_SEP + "Diet:" + diet
				+ ITEM_SEP + "Live in:" + livedIn + ITEM_SEP + "Type:"
				+ type + ITEM_SEP + "Species:" + species + ITEM_SEP + "Period:" + periodName
				+ ITEM_SEP + "LengthMeters:" + lengthMeters + ITEM_SEP + "Favorite:" + favorite;
	}

}