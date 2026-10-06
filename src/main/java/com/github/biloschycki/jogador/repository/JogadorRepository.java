package com.github.biloschycki.jogador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.github.biloschycki.jogador.model.Jogador;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {
    

}
