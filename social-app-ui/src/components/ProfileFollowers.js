import axios from "axios";
import React, { useState, useEffect } from "react";
import { Link, useParams, withRouter } from "react-router-dom";
import LoadingDotsIcon from "./LoadingDotsIcon";
import Page from "./Page";

function ProfileFollowers() {
  const { profileId } = useParams(); //object de-structuring
  const [isLoading, setIsLoading] = useState(true);
  const [followers, setFollowers] = useState([]);

  useEffect(() => {
    async function fetchPosts() {
      try {
        console.log("Going to fetch followers of user of profileId:: " + profileId);
        const response = await axios.get(`/profile/${profileId}/followers`);
        console.log(response.data);
        setIsLoading(false);
        setFollowers(response.data);
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
    <Page title="Profile-Followers">
      <div className="list-group">
        {followers.map((follower) => {
          const date = new Date(follower.follwingTimestamp);
          const dateFormatted = `${date.getDate()}/${date.getMonth() + 1}/${date.getFullYear()}`;

          //date.toLocaleString('en-US', { hour: 'numeric', minute: 'numeric', hour12: true })
          //const timeFormatted = `${date.getHours()} : ${date.getMinutes()}`;
          const timeFormatted = date.toLocaleString("en-US", {
            hour: "numeric",
            minute: "numeric",
            hour12: true,
          });
          
          return (
            <Link
              key={follower.profileId}
              to={`/profile/${follower.profileId}`}
              className="list-group-item list-group-item-action"
            >
              <img
                className="avatar-tiny"
                src="../images/user.png"
                style={{ width: "3em", height: "3em" }}
                alt="user-img"
              />{" "}
              <strong>{follower.firstName} {" "}  {follower.lastName}</strong>{" "}
              <span className="text-muted small">  {" "}Following me From ::{" "}
              {dateFormatted}{" "} at {" "}{timeFormatted}{" "}
              </span>
            </Link>
          );
        })}
      </div>
    </Page>
  );
}
export default withRouter(ProfileFollowers);
