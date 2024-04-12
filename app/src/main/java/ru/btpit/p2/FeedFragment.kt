package ru.btpit.p2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dto.Post
import ru.btpit.p2.MainActivity.Companion.textArg
import ru.btpit.p2.databinding.FragmentFeedBinding
import viewmodel.PostViewModel

class FeedFragment : Fragment() {
    private  val newPostRequestCode = 1
    private  val  viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )
        val adapter = PostsActivity (object : OnInteractionListener {
            override fun onEdit(post:Post) {
                viewModel.edit(post)
                println(post)
//                val text = post.content
//                newPostActivity.launch(text)
                val bundle = Bundle()
                bundle.textArg = post.content
                findNavController().navigate(R.id.action_feedFragment_to_newPostFragment2, bundle)
            }

            override fun onLike(post: Post) {
                viewModel.like(post.id)
            }
            override fun onShare(post: Post)
            {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
                viewModel.share(post.id)
            }
            override fun onVideo(post: Post)
            {
                val url = "https://www.youtube.com/watch?v=zl49slpr7xA"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }
            override fun onAuthorClicked(post: Post) {
                val bundle = Bundle()
                bundle.putLong("postId", post.id)
                findNavController().navigate(R.id.action_feedFragment_to_mainActivity, bundle)

            }

        })



        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }



        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment2)
        }
        return binding.root

    }
}