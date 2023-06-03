package com.capstone.tribillfine.domain.user;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TkRepository {
    private List<String> tk = new ArrayList<>();

    public void save(String token){
        tk.add(token);
    }
    public List<String> findAll(){
        return tk;
    }
    public void deleteAll(){
        tk.clear();
    }

}
