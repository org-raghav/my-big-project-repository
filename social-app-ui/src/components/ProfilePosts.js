import axios from "axios";
import React, { useState, useEffect } from "react";
import { Link, useParams, withRouter } from "react-router-dom";
import LoadingDotsIcon from "./LoadingDotsIcon";
import Page from "./Page";

function ProfilePosts() {
  const { profileId } = useParams(); //object de-structuring
  const [isLoading, setIsLoading] = useState(true);
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    async function fetchPosts() {
      try {
        console.log("Going to fetch posts of userId:: " + profileId);

        const response = await axios.get(`/profile/${profileId}/posts`);
        console.log(response.data);
        setIsLoading(false);
        setPosts(response.data);
      } catch (ex) {
        console.log("An Exception occured during fetchPosts:: " + ex);
      }
    }
    fetchPosts();
  }, [profileId]);

  if (isLoading) {
    return (
      <Page title="...">
        <LoadingDotsIcon />
      </Page>
    );
  }

  return (
    <Page title="Profile-Post">
      <div className="list-group">
        {posts.map((post) => {
          const date = new Date(post.createdDate);
          const dateFormatted = `${date.getDate()}/${
            date.getMonth() + 1
          }/${date.getFullYear()}`;

          //date.toLocaleString('en-US', { hour: 'numeric', minute: 'numeric', hour12: true })
          //const timeFormatted = `${date.getHours()} : ${date.getMinutes()}`;
          const timeFormatted = date.toLocaleString("en-US", {
            hour: "numeric",
            minute: "numeric",
            hour12: true,
          });

          return (
            <Link
              key={post.postId}
              to={`/posts/${post.postId}`}
              className="list-group-item list-group-item-action"
            >
              <img
                className="avatar-tiny"
                src="../images/user.png"
                style={{ width: "3em", height: "3em" }}
                alt="user-img"
              />{" "}
              <strong>{post.title}</strong>{" "}
              <span className="text-muted small">
                on {dateFormatted} at {timeFormatted}{" "}
              </span>
            </Link>
          );
        })}
      </div>
    </Page>
  );
}
export default withRouter(ProfilePosts);
