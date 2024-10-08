package ru.tomatojAI.analytics_builder_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "subscriptions")
@NoArgsConstructor
public class Subscriptions {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @Column(name = "id", nullable = false)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private Users user;

        @Column(name = "query")
        private String query;

        public Subscriptions(Users user, String query) {
                this.user = user;
                this.query = query;
        }
}