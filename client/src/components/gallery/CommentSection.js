import { Avatar, Divider, Grid, Paper } from "@material-ui/core";
import React, {
  useContext,
  useState,
  useEffect,
  useRef,
  useCallback,
} from "react";
import { apiContext } from "../../store/ApiContext";
import { CommentInput } from "./CommentInput";
import classes from "./sideStyle.module.css";
import { useParams } from "react-router-dom";
import MoreHorizIcon from "@mui/icons-material/MoreHoriz";
import { IconButton } from "@mui/material";
import { Scrollbars } from "react-custom-scrollbars";
import { galleryContext } from "./../../store/GalleryContext";

const imgLink =
  "https://images.pexels.com/photos/1681010/pexels-photo-1681010.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260";

export function CommentSection() {
  const { serverUrl, photoLoadComments, photoComments } =
    useContext(apiContext);
  const { comments, setComments } = useContext(galleryContext);
  const [load, setLoad] = useState(false);
  const { photoId } = useParams();
  const scrollRef = useRef();

  useEffect(() => {
    setLoad(true);
    photoComments(photoId)
      .then((x) => {
        setComments(x);
      })
      .finally(() => {
        setLoad(false);
      });
  }, [setComments, photoComments, photoId]);

  console.log(comments);

  const scrollHandler = useCallback(
    (event) => {
      console.log(event.target.scrollTop === 0);
      console.log(comments.at(-1).commentId);
      console.log(comments);
      if (event.target.scrollTop === 0)
        photoLoadComments(photoId, comments.at(0)?.commentId).then((x) => {
          !!x.length && setComments([ ...x, ...comments]);
        });
    },
    [comments, setComments, setLoad, load, photoLoadComments]
  );

  return (
    <Paper
      classes={{ root: classes.commentSection }}
      sx={{ width: 500, height: 450, overflowY: "scroll" }}
    >
      <h1>Comments</h1>
      <Paper style={{ padding: "40px 20px" }}>
        <Scrollbars
          onScroll={scrollHandler}
          maxWidth={"100%"}
          autoHeight
          autoHeightMax={"40vh"}
        >
          {comments.map((item) => (
            <Grid container wrap="nowrap" spacing={2}>
              <Grid item>
                <Avatar
                  alt="Remy Sharp"
                  src={`${serverUrl + "images/" + item.avatarPath}`}
                />
              </Grid>
              <Grid justifyContent="left" item xs zeroMinWidth>
                <h4 style={{ margin: 0, textAlign: "left" }}>
                  {item.username}
                </h4>
                <p style={{ textAlign: "left" }}>{item.commentText} </p>
                <p style={{ textAlign: "left", color: "gray" }}>{item.date}</p>
                <Divider variant="fullWidth" style={{ margin: "30px 0" }} />
              </Grid>
            </Grid>
          ))}
        </Scrollbars>
        <CommentInput />
      </Paper>
    </Paper>
  );
}
