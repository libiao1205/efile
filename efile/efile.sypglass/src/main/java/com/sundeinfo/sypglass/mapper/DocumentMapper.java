package com.sundeinfo.sypglass.mapper;

import com.sundeinfo.sypglass.model.Document;
import com.sundeinfo.sypglass.model.Folder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface DocumentMapper {

    /*取得本级目录下所有资源*/
    List<Document> selectDocumentByParentid(@Param("id") long id);

    /*取得本级目录下所有资源*/
    List<Document> selectDocumentByParentidAndUserid(@Param("id") long id,@Param("userid") long userid,@Param("sortOrder") String sortOrder);

    /*取得指定资源*/
    Document selectDocumentByid(@Param("id") long id, @Param("kind") String kind);

    /*向下取得部门列表*/
    List<Folder> selectFolderAndChildrenById(@Param("id") long id);

    /*根节点搜索数据*/
    List<Folder> selectFolderFromRoot();

    /*向下取得部门列表、不排除被删除文件夹或者文件*/
    List<Folder> selectFolderAndChildrenByIdWithDelete(@Param("id") long id);

    /*向上取得部门列表*/
    List<Folder> selectFolderAndParentsById(@Param("id") long id);

    /*获取回收站信息列表*/
    List<Document> selectTrashDocument();

    /*获取回收站信息 通过文件名关键字*/
    List<Document> selectTrashDocumentBySearchKey(@Param("word") String word);

    /*获取用户回收站信息 通过删除用户id*/
    List<Document> selectTrashDocumentByUserId(@Param("userId") long userId);

    /*获取用户回收站信息 通过删除用户id和文件名关键字*/
    List<Document> selectTrashDocumentByUserIdAndSearchKey(@Param("userId") long userId,@Param("word") String word);

    /*获取回收站信息 通过删除用户id或父文件夹名称*/
    List<Document> selectTrashDocumentByFolderName(@Param("userId") long userId,@Param("folders") List<String> folders);

    /*获取回收站信息 通过删除用户id或父文件夹名称 和文件名关键字*/
    List<Document> selectTrashDocumentByFolderNameAndSearchKey(@Param("word") String word,@Param("userId") long userId,@Param("folders") List<String> folders);

    /*获取已经被彻底删除的文件信息*/
    List<Document> selectDeletedDocument();

//    /*关键字检索*/
//    List<Document> selectDocumentLikeKey(@Param("key") String key);

    /*结巴分词检索*/
    List<Document> selectDocumentLikeKeyWithJieba(@Param("words") List<String> words,@Param("pagenum") int pagenum,@Param("pagesize") int pagesize);

    /*结巴分词检索删除文件*/
    List<Document> selectDeletedDocumentLikeKeyWithJieba(@Param("words") List<String> words);

}
