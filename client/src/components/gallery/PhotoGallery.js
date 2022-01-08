import Bar from "../Bar";
import { PhotoSlide } from "./PhotoSlide";
import { CommentSection } from "./CommentSection";
import classes from "./sideStyle.module.css";
import { DescribeSection } from "./DescribeSection";
import RateSection from "./RateSection";
import GalleryContext from "../../store/GalleryContext";

export function PhotoGallery() {
  return (
    <>
      <Bar />
      <PhotoSlide />
      <div className={classes.commentAndRateSection}>
        <GalleryContext>
          <div>
            <DescribeSection />
            <CommentSection />
          </div>
          <RateSection />
        </GalleryContext>
      </div>
    </>
  );
}
