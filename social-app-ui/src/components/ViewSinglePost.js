import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, withRouter, useParams } from "react-router-dom";
import LoadingDotsIcon from "./LoadingDotsIcon";
import Page from "./Page";

function ViewSinglePost() {
  const { postId } = useParams(); //object de-structuring
  const [isLoading, setIsLoading] = useState(true);
  const [post, setPost] = useState([]);
  
  useEffect(() =>{

    //step:1-axios generate a cancel token to cancel the reuest
    const ourRequest = axios.CancelToken.source();

    try {
      async function fetchPost(){
        //step:2-set cancelToken to actual request  
        const response = await axios.get(`/posts/${postId}`, {cancelToken:ourRequest.token});
        console.log(response.data);
        setPost(response.data);
        setIsLoading(false);
      }fetchPost();    
    } catch (ex) {
      console.log(" NO fetch:problem or request canceled " + ex);
    }

    return () => {
      //step:3-cancel the request on componentUnmount
      ourRequest.cancel();
    }

  }, [postId]);

  if (isLoading){
    return (
      <Page title="...">
        <LoadingDotsIcon />
      </Page>
    );
  }

  const date = new Date(post.createdDate);
  const dateFormatted = `${date.getDate()}/${date.getMonth() + 1}/${date.getFullYear()}`;
  const timeFormatted = date.toLocaleString('en-US', { hour: 'numeric', minute: 'numeric', hour12: true });

  return (
    
    <Page title="Post">
      <div className="d-flex justify-content-between">
        <h2>{post.title}</h2>
        <span className="pt-2">
          <Link href="#" className="text-primary mr-2" title="Edit">
            <i className="fas fa-edit"></i>
          </Link>
          <Link className="delete-post-button text-danger" title="Delete">
            <i className="fas fa-trash"></i>
          </Link>
        </span>
      </div>

      <p className="text-muted small mb-4">
        <Link to={`/profile/${post.createdBy}`}>
          <img alt="avatar"
            className="avatar-tiny"
            src="https://gravatar.com/avatar/b9408a09298632b5151200f3449434ef?s=128"
          />
        </Link>
        Posted by <strong>{post.creatorName}</strong> on {" "} {dateFormatted}{" "}  at{" "}  {timeFormatted}
      </p>

      <div className="body-content">
        <p>{post.body}</p>
      </div>
    </Page>
  );
}

export default withRouter(ViewSinglePost);
