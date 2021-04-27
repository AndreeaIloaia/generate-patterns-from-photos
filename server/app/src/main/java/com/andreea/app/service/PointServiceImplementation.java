package com.andreea.app.service;

import com.andreea.app.models.PatternEntity;
import com.andreea.app.models.PointEntity;
import com.andreea.app.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PointServiceImplementation {
    @Autowired
    PointRepository pointRepository;


    public List<PointEntity> getCoordinates(PatternEntity patternEntity) {
        //TODO - bazinga function
        //momentan generam random niste valori
        /*
        let elem : coords = {x: 25, y:25};
      elem = {x:150, y:100};
      elem = {x:280, y:100};
      elem = {x:395, y:25};
      elem = {x:430, y:120};
      elem = {x:360, y:150};
      elem = {x:385, y:450};
      elem = {x:55, y:450};
      elem = {x:75, y:150};
      elem = {x:0, y:120};
         */
        List<PointEntity> list = new ArrayList<>();
        PointEntity pointEntity = new PointEntity(35, 25);
        list.add(pointEntity);
        pointRepository.save(pointEntity);

//        PointEntity pointEntity2 = new PointEntity(160, 100);
        PointEntity pointEntity2 = new PointEntity(160, 200);
        list.add(pointEntity2);
        pointRepository.save(pointEntity2);


        PointEntity pointEntity11 = new PointEntity(225,300);
        list.add(pointEntity11);
        pointRepository.save(pointEntity11);

//        PointEntity pointEntity3 = new PointEntity(290,100);
        PointEntity pointEntity3 = new PointEntity(290,200);
        list.add(pointEntity3);
        pointRepository.save(pointEntity3);


        PointEntity pointEntity4 = new PointEntity(405,25);
        list.add(pointEntity4);
        pointRepository.save(pointEntity4);

        PointEntity pointEntity5 = new PointEntity(440,120);
        list.add(pointEntity5);
        pointRepository.save(pointEntity5);

        PointEntity pointEntity6 = new PointEntity(370,150);
        list.add(pointEntity6);
        pointRepository.save(pointEntity6);

        PointEntity pointEntity7 = new PointEntity(395,450);
        list.add(pointEntity7);
        pointRepository.save(pointEntity7);

        PointEntity pointEntity8 = new PointEntity(65,450);
        list.add(pointEntity8);
        pointRepository.save(pointEntity8);

        PointEntity pointEntity9 = new PointEntity(85,150);
        list.add(pointEntity9);
        pointRepository.save(pointEntity9);

        PointEntity pointEntity10 = new PointEntity(10,120);
        list.add(pointEntity10);
        pointRepository.save(pointEntity10);

        return list;
    }
}
