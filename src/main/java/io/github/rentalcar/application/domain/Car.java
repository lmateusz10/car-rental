package io.github.rentalcar.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.rentalcar.application.domain.enumeration.TransmissionType;

/**
 * A Car.
 */
@Entity
@Table(name = "car")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "license_plate", nullable = false)
    private String licensePlate;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "transmission", nullable = false)
    private TransmissionType transmission;

    @NotNull
    @Column(name = "aircondition", nullable = false)
    private Boolean aircondition;

    @NotNull
    @Column(name = "doors", nullable = false)
    private Integer doors;

    @Column(name = "keywords")
    private String keywords;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "date_added")
    private LocalDate dateAdded;

    @Column(name = "date_modified")
    private LocalDate dateModified;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Lob
    @Column(name = "image_1")
    private byte[] image_1;

    @Column(name = "image_1_content_type")
    private String image_1ContentType;

    @Lob
    @Column(name = "image_2")
    private byte[] image_2;

    @Column(name = "image_2_content_type")
    private String image_2ContentType;

    @OneToMany(mappedBy = "car")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reservation> cars = new HashSet<>();
    @ManyToMany(mappedBy = "cars")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Category> categories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Car licensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
        return this;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getName() {
        return name;
    }

    public Car name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TransmissionType getTransmission() {
        return transmission;
    }

    public Car transmission(TransmissionType transmission) {
        this.transmission = transmission;
        return this;
    }

    public void setTransmission(TransmissionType transmission) {
        this.transmission = transmission;
    }

    public Boolean isAircondition() {
        return aircondition;
    }

    public Car aircondition(Boolean aircondition) {
        this.aircondition = aircondition;
        return this;
    }

    public void setAircondition(Boolean aircondition) {
        this.aircondition = aircondition;
    }

    public Integer getDoors() {
        return doors;
    }

    public Car doors(Integer doors) {
        this.doors = doors;
        return this;
    }

    public void setDoors(Integer doors) {
        this.doors = doors;
    }

    public String getKeywords() {
        return keywords;
    }

    public Car keywords(String keywords) {
        this.keywords = keywords;
        return this;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public Car description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRating() {
        return rating;
    }

    public Car rating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public Car dateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public LocalDate getDateModified() {
        return dateModified;
    }

    public Car dateModified(LocalDate dateModified) {
        this.dateModified = dateModified;
        return this;
    }

    public void setDateModified(LocalDate dateModified) {
        this.dateModified = dateModified;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public Car sortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public byte[] getImage_1() {
        return image_1;
    }

    public Car image_1(byte[] image_1) {
        this.image_1 = image_1;
        return this;
    }

    public void setImage_1(byte[] image_1) {
        this.image_1 = image_1;
    }

    public String getImage_1ContentType() {
        return image_1ContentType;
    }

    public Car image_1ContentType(String image_1ContentType) {
        this.image_1ContentType = image_1ContentType;
        return this;
    }

    public void setImage_1ContentType(String image_1ContentType) {
        this.image_1ContentType = image_1ContentType;
    }

    public byte[] getImage_2() {
        return image_2;
    }

    public Car image_2(byte[] image_2) {
        this.image_2 = image_2;
        return this;
    }

    public void setImage_2(byte[] image_2) {
        this.image_2 = image_2;
    }

    public String getImage_2ContentType() {
        return image_2ContentType;
    }

    public Car image_2ContentType(String image_2ContentType) {
        this.image_2ContentType = image_2ContentType;
        return this;
    }

    public void setImage_2ContentType(String image_2ContentType) {
        this.image_2ContentType = image_2ContentType;
    }

    public Set<Reservation> getCars() {
        return cars;
    }

    public Car cars(Set<Reservation> reservations) {
        this.cars = reservations;
        return this;
    }

    public Car addCar(Reservation reservation) {
        this.cars.add(reservation);
        reservation.setCar(this);
        return this;
    }

    public Car removeCar(Reservation reservation) {
        this.cars.remove(reservation);
        reservation.setCar(null);
        return this;
    }

    public void setCars(Set<Reservation> reservations) {
        this.cars = reservations;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Car categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Car addCategory(Category category) {
        this.categories.add(category);
        category.getCars().add(this);
        return this;
    }

    public Car removeCategory(Category category) {
        this.categories.remove(category);
        category.getCars().remove(this);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        if (car.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), car.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Car{" +
            "id=" + getId() +
            ", licensePlate='" + getLicensePlate() + "'" +
            ", name='" + getName() + "'" +
            ", transmission='" + getTransmission() + "'" +
            ", aircondition='" + isAircondition() + "'" +
            ", doors=" + getDoors() +
            ", keywords='" + getKeywords() + "'" +
            ", description='" + getDescription() + "'" +
            ", rating=" + getRating() +
            ", dateAdded='" + getDateAdded() + "'" +
            ", dateModified='" + getDateModified() + "'" +
            ", sortOrder=" + getSortOrder() +
            ", image_1='" + getImage_1() + "'" +
            ", image_1ContentType='" + getImage_1ContentType() + "'" +
            ", image_2='" + getImage_2() + "'" +
            ", image_2ContentType='" + getImage_2ContentType() + "'" +
            "}";
    }
}
