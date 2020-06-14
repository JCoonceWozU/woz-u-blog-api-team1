package com.wozu.blog.models;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "user")
class User
{
    String email;
    String password;
    String firstName = "";
    String lastName = "";
    String bio = "";
    String imageUrl = "";
    String socialLink = "";
    boolean isPaid = false;
    OffsetDateTime createdAt = OffsetDateTime.now();
    OffsetDateTime updatedAt = OffsetDateTime.now();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getBio()
    {
        return bio;
    }

    public void setBio(String bio)
    {
        this.bio = bio;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getSocialLink()
    {
        return socialLink;
    }

    public void setSocialLink(String socialLink)
    {
        this.socialLink = socialLink;
    }

    public boolean isPaid()
    {
        return isPaid;
    }

    public void setPaid(boolean paid)
    {
        isPaid = paid;
    }

    public OffsetDateTime getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt)
    {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }
}