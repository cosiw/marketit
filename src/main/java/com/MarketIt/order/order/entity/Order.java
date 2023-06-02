package com.MarketIt.order.order.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity(name="Torder")
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderIdx;
    private String itemName;
    private Long userIdx;
    private String address;
    private String status;
    private Long completeUser;

    @Builder
    public Order(Long orderIdx, String itemName, Long userIdx, String address, String status, Long completeUser){
        this.orderIdx = orderIdx;
        this.itemName = itemName;
        this.userIdx = userIdx;
        this.address = address;
        this.status = status;
        this.completeUser = completeUser;
    }

    @PrePersist
    public void prePersist(){
        this.status = "R";
        this.completeUser = 0L;
    }

    public void completeOrder(Long completeUser){
        this.status = "C";
        this.completeUser = completeUser;

    }
}
