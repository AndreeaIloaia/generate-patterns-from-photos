package com.andreea.app.service;

import com.andreea.app.auth.UserPrincipal;
import com.andreea.app.dtos.GarmentsDto;
import com.andreea.app.models.*;
import com.andreea.app.repository.GarmentRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GarmentServiceImplementation {

    @Autowired
    UserServiceImplementation userServiceImplementation;
    @Autowired
    GraphServiceImplementation graphServiceImplementation;
    @Autowired
    FileServiceImplementation fileServiceImplementation;
    @Autowired
    GarmentRepository garmentRepository;

    @Autowired
    ServletContext context;

    private UserEntity getCurrentUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long id = userPrincipal.getId();
        Optional<UserEntity> user = userServiceImplementation.findById(id);

        if (user.isEmpty()) {
            throw new Exception("No user with the specified id.");
        }
        return user.get();
    }

    public GarmentEntity saveGarment(String type) throws Exception {
        UserEntity userEntity = getCurrentUser();

        GarmentEntity garmentEntity = new GarmentEntity();

        garmentEntity.setType(type);
        garmentEntity.setUser(userEntity);

        return garmentRepository.save(garmentEntity);
    }


//    /**
//     * Metoda face legatura dintre scriptul de python de clasificare si server
//     *
//     * @param fileEntity - numele fisierului pe care il dam spre scriptul python
//     * @return lista de denumiri obtinute
//     */
//    public GarmentTypeDto getType(FileEntity fileEntity, Long id) throws Exception {
////        GarmentEntity garmentEntity = saveGarment("nu");
////        FileEntity fileEntity = fileServiceImplementation.upload(file, id);
//        String path = "D:/Facultate/Licenta/generate-patterns-from-photos/server/app/fileStorage/";
//        String fileName = path + fileEntity.getFileName();
//
//        String url = "http://127.0.0.1:5000/";
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        HttpPost httppost = new HttpPost(url);
//
//        List<BasicNameValuePair> params = new ArrayList<>(2);
//        params.add(new BasicNameValuePair("url", fileName));
//
//        httppost.setEntity(new UrlEncodedFormEntity(params));
//
//        CloseableHttpResponse response = httpclient.execute(httppost);
//        HttpEntity entity = response.getEntity();
//
//        if (entity != null) {
//            String responseString = EntityUtils.toString(entity);
//            String listTypes = responseString.split(":")[1].replace("\"", "")
//                    .replace("[", "")
//                    .replace("]", "")
//                    .replace("\n", "")
//                    .replace("}", "");
//            String[] a = new String[0];
//            List<String> types = new ArrayList<>();
//            if (listTypes.contains(",")) {
//                a = listTypes.split(",");
//                types = Arrays.asList(a);
//            } else {
//                types.add(listTypes);
//            }
//            System.out.println(types);
//            return new GarmentTypeDto(id, types);
//        }
//        return null;
//    }

    public void saveGraph(GraphDto graphDto) {
        HashMap<Long, NodeGraph> graph = new HashMap<>();
        GarmentEntity garmentEntity = garmentRepository.findById(Long.parseLong(graphDto.getId())).get();
        graph = graphServiceImplementation.saveGraph(garmentEntity, graphDto);

    }

    public GraphDto loadGraph(String idGarment, String idOption) {
        return graphServiceImplementation.loadGraph(Long.parseLong(idGarment), Long.parseLong(idOption));
    }

    public GarmentsDto getGarments() throws Exception {
        UserEntity userEntity = getCurrentUser();
        List<String> ids = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();
        HashMap<Long, String> myMap = new HashMap<>();
        List<GarmentEntity> ceva = garmentRepository.findAllByUser(userEntity).stream()
                .sorted(Comparator.comparingLong(GarmentEntity::getId)).collect(Collectors.toList());
        for(GarmentEntity e:ceva) {
            myMap.put(e.getId(), fileServiceImplementation.getFileNameForAGivenGarment(e.getId()));
            fileNames.add(fileServiceImplementation.getFileNameForAGivenGarment(e.getId()));
            ids.add(String.valueOf(e.getId()));
        }

        List<String> images = new ArrayList<>();
        String filesPath = "D:\\Facultate\\Licenta\\generate-patterns-from-photos\\server\\app\\fileStorage";
        File fileFolder = new File(filesPath);

        HashMap<String, String> myFilesMap = new HashMap<>();
        if (fileFolder != null) {
            for (final File file : fileFolder.listFiles()) {
                if (!file.isDirectory() && fileNames.contains(file.getName())) {
                    String encoderBase64 = null;
                    try {
                        String extension = FilenameUtils.getExtension(file.getName());

                        FileInputStream fileInputStream = new FileInputStream(file);
                        byte[] bytes = new byte[(int) file.length()];
                        fileInputStream.read(bytes);
                        encoderBase64 = Base64.getEncoder().encodeToString(bytes);
                        String data = "data:image/" + extension + ";base64," + encoderBase64;
                        images.add(data);
                        fileInputStream.close();
                        myFilesMap.put(file.getName(), data);
                    } catch (Exception e) {
                        System.out.println(e.getMessage() + "at GarmentServiceImplementation getGarments function");
                    }
                }
            }
        }

        List<String> lastList = new ArrayList<>();
        for (Map.Entry<Long, String> entry : myMap.entrySet()) {
            String data = myFilesMap.get(entry.getValue());
            lastList.add(data);
        }
        return new GarmentsDto(ids, lastList);
    }

    public boolean saveFilesForGarment(GarmentEntity garmentEntity, MultipartFile[] files) {
        for (MultipartFile f : files) {
            try {
                fileServiceImplementation.upload(f, garmentEntity);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}
