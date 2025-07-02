import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.io.File;
import java.util.List;

public class RecommendationSystem {
    public static void main(String[] args) {
        try {
            // Step 1: Load data
            DataModel model = new FileDataModel(new File("data/data.csv")); // path inside project

            // Step 2: Compute similarity
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // Step 3: Find user neighborhood
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

            // Step 4: Build recommender
            GenericUserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Step 5: Recommend for user ID 1 (you can change the ID)
            List<RecommendedItem> recommendations = recommender.recommend(1, 3);

            for (RecommendedItem recommendation : recommendations) {
                System.out.println("Recommended Item ID: " + recommendation.getItemID() +
                                   " | Estimated Preference: " + recommendation.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
