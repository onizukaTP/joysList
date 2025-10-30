package com.tpdev.joysList.entity.category;

import com.tpdev.joysList.entity.Ad;
import com.tpdev.joysList.entity.enums.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventAd extends Ad {
    @ElementCollection(targetClass = EventType.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "event_ad_event_types",
            joinColumns = @JoinColumn(name = "event_ad_id")
    )
    @Column(name = "event_type")
    private List<EventType> eventTypes;
}
