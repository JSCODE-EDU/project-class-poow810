package com.example.jscodestudy.repository;

import com.example.jscodestudy.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
