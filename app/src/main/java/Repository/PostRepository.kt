package Repository

import androidx.lifecycle.LiveData
import dto.Post


interface PostRepository {
    fun getAll(): LiveData<List<Post>>

fun save(post: Post)
    fun like(id: Long)
    fun share(id: Long)
    fun removeById(id: Long)

    fun postID(id: Long): LiveData<Post>
//   fun onEdit(post: Post)
}