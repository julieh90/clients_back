package com.alianza.clients.domains.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client implements Serializable {

    @Id
    @Column(name = "shared_key")
    private String sharedKey;

    @Column(name = "business_id")
    private String businessId;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private Long phone;

    @Column(name = "start_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startDate;

    @Column(name = "end_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endDate;



}
