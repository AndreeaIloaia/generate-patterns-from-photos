package com.andreea.app.service;

import com.andreea.app.models.*;
import com.andreea.app.repository.NodeRepository;
import com.andreea.app.repository.Point3DRepository;
import com.andreea.app.repository.SeamPointRepository;
import com.andreea.app.repository.SeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class GraphServiceImplementation {
    @Autowired
    Point3DRepository point3DRepository;
    @Autowired
    NodeRepository nodeRepository;
    @Autowired
    SeamRepository seamRepository;
    @Autowired
    SeamPointRepository seamPointRepository;

    public HashMap<Long, NodeGraph> saveGraph(GarmentEntity garmentEntity, GraphDto graphDto) {
        HashMap<Long, NodeGraph> graph = new HashMap<>();

        //daca exista deja puncte pentru un item garment, atunci trebuie facut un update - stergem datele si le adaugam pe cele noi
        point3DRepository.findAllByGarmentEntity_Id(garmentEntity.getId()).forEach(e -> {
            seamPointRepository.deleteAll(seamPointRepository.findAllByPoint3DEntity(e));
            point3DRepository.delete(e);
        });
        seamRepository.findAllByGarmentEntity_Id(garmentEntity.getId()).forEach(e -> {
            seamRepository.delete(e);
        });

        for (int i = 0; i < graphDto.getVertices().size(); i++) {
            List<Double> coords = graphDto.getVertices().get(i);
            //adauga punctele 3d
            Point3DEntity point3DEntity = new Point3DEntity();
            point3DEntity.setNumber((long) i);
            point3DEntity.setX(coords.get(0));
            point3DEntity.setY(coords.get(1));
            point3DEntity.setZ(coords.get(2));
            point3DEntity.setGarmentEntity(garmentEntity);
            Point3DEntity savedPoint = point3DRepository.save(point3DEntity);

            //adauga muchiile dintre ele
            List<Long> listEdges = graphDto.getEdges().get(i);
            for (Long node: listEdges) {
                NodeEntity nodeE = new NodeEntity();
                nodeE.setStart((long)i);
                nodeE.setEnd(node);
                nodeE.setGarmentEntity(garmentEntity);
                nodeRepository.save(nodeE);
            }
            NodeGraph node = new NodeGraph(savedPoint, listEdges);
            graph.put((long) i, node);
        }

        //adauga cusaturile
        for (int i = 0; i < graphDto.getSeams().size(); i++) {
            SeamEntity seamEntity = new SeamEntity();
            seamEntity.setNumber((long) i);
            seamEntity.setGarmentEntity(garmentEntity);
            seamRepository.save(seamEntity);
            for(Long vertice: graphDto.getSeams().get(i)) {
                SeamPointEntity seamPointEntity = new SeamPointEntity();
                seamPointEntity.setPoint3DEntity(point3DRepository.findByNumberAndGarmentEntity(vertice, garmentEntity).get());
                seamPointEntity.setSeamEntity(seamEntity);
                seamPointRepository.save(seamPointEntity);
            }
        }
        return graph;
    }

    public GraphDto loadGraph(Long idGarment, Long idOption) {
        //daca nu exista un graf pentru itemul dorit, se va incarca cel de baza cu id = 583
        if(point3DRepository.findAllByGarmentEntity_Id(idGarment).size() == 0) {
            idGarment = idOption;
        }

        List<List<Long>> seams = new ArrayList<>();
        List<List<Double>> vertices = new ArrayList<>();
        seamRepository.findAllByGarmentEntity_Id(idGarment).forEach(e -> {
            List<Long> seam = new ArrayList<>();
            seamPointRepository.findAllBySeamEntity(e).forEach(s -> {
                Point3DEntity point3DEntity = s.getPoint3DEntity();
                seam.add(point3DEntity.getNumber());
            });
            seams.add(seam);
        });

//        var ceva = point3DRepository.findAllByGarmentEntity_Id(idGarment);
        point3DRepository.findAllByGarmentEntity_Id(idGarment).stream()
                .sorted(Comparator.comparingLong(Point3DEntity::getNumber)).forEach(e -> {
                List<Double> coords = new ArrayList<>();
                coords.add(e.getX());
                coords.add(e.getY());
                coords.add(e.getZ());
                vertices.add(coords);
        });

        GraphDto graphDto = new GraphDto();
        graphDto.setId(String.valueOf(idGarment));
        graphDto.setVertices(vertices);
        graphDto.setSeams(seams);
        return graphDto;
    }
}
