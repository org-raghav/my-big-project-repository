import axios from "axios";
import React, { useState, useEffect } from "react";
import { Link, useParams, withRouter } from "react-router-dom";
import LoadingDotsIcon from "./LoadingDotsIcon";
import Page from "./Page";

function ProfileFollowings() {
  const { profileId } = useParams(); //object de-structuring
  const [isLoading, setIsLoading] = useState(true);
  const [followings, setFollowings] = useState([]);

  useEffect(() => {
    async function fetchPosts() {
      try {
        console.log("Going to fetch followings of user of profileId:: " + profileId);
        const response = await axios.get(`/profile/${profileId}/followings`);
        console.log(response.data);
        setIsLoading(false);
        setFollowings(response.data);
      } catch (ex) {
        console.log("An Exception occured during fetch followings:: " + ex);
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
        {followings.map((following) => {
          const date = new Date(following.follwingTimestamp);
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
              key={following.profileId}
              to={`/profile/${following.profileId}`}
              className="list-group-item list-group-item-action"
            >
              <img
                className="avatar-tiny"
                src="../images/user.png"
                style={{ width: "3em", height: "3em" }}
                alt="user-img"
              />{" "}
              <strong>{following.firstName} {" "}  {following.lastName}</strong>{" "}
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
export default withRouter(ProfileFollowings);
