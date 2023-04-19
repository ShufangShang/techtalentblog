package com.techtalentsouth.techtalentblog.BlogPost;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.DeleteMapping;

@Controller
public class BlogPostController {

    private BlogPost blogPost;
    
    @Autowired
    private BlogPostRepository blogPostRepository;
    private static List<BlogPost> posts = new ArrayList<>();
    
   /*  @GetMapping(value = "/")
    public String index(BlogPost blogPost,  Model model) {
        model.addAttribute("posts", posts);
        return "blogpost/index";
    } */ 
    
    @GetMapping(value = "/")
    public String index(BlogPost blogPost, Model model) {
        posts.removeAll(posts);
        for (BlogPost post : blogPostRepository.findAll()) {
            posts.add(post);
        }
        model.addAttribute("posts", posts);
        return "blogpost/index";
    }

    @GetMapping(value = "/blogposts/new")
    public String newBlog (BlogPost blogPost) {
        return "blogpost/new";
    }

    @PostMapping(value="/blogposts")
    public String addNewBlogPost(BlogPost blogPost, Model model) {
        //blogPostRepository.save(new BlogPost(blogPost.getTitle(), blogPost.getAuthor(), blogPost.getBlogEntry()));
        BlogPost bP = blogPostRepository.save(blogPost);
        //posts.add(bP);
        model.addAttribute("id", bP.getId());
        model.addAttribute("title", bP.getTitle());
        model.addAttribute("author", bP.getAuthor());
        model.addAttribute("blogEntry", bP.getBlogEntry());
        return "blogpost/result";
    }
    
    @RequestMapping(value = "/blogposts/{id}", method = {RequestMethod.DELETE})
    public String deletePostWithId(@PathVariable Long id, BlogPost blogPost) {
    
        blogPostRepository.deleteById(id);
       /*  BlogPost bP = blogPostRepository.save(blogPost);
        posts.add(bP); */
       /*  model.addAttribute("id", blogPost.getId());
        model.addAttribute("title", blogPost.getTitle());
        model.addAttribute("author", blogPost.getAuthor());
        model.addAttribute("blogEntry", blogPost.getBlogEntry());
        */
        return "blogpost/index";
    
    }   
   

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.GET)
    public String editPostWithId(@PathVariable Long id, BlogPost blogPost, Model model) {
        Optional<BlogPost> post = blogPostRepository.findById(id);
        if (post.isPresent()) {
            BlogPost actualPost = post.get();
            model.addAttribute("blogPost", actualPost);
        }
        return "blogpost/edit";
    }
   
    @RequestMapping(value = "/blogposts/update/{id}")
    public String updateExistingPost(@PathVariable Long id, BlogPost blogPost, Model model) {
        Optional<BlogPost> post = blogPostRepository.findById(id);
        if (post.isPresent()) {
            BlogPost actualPost = post.get();
            actualPost.setTitle(blogPost.getTitle());
            actualPost.setAuthor(blogPost.getAuthor());
            actualPost.setBlogEntry(blogPost.getBlogEntry());
            blogPostRepository.save(actualPost);
            model.addAttribute("blogPost", actualPost);
        }

        return "blogpost/result";
    }

    @RequestMapping(value = "blogposts/delete/{id}")
    public String deletePostById(@PathVariable Long id, BlogPost blogPost) {
        blogPostRepository.deleteById(id);
        return "blogpost/delete";
    }

}
