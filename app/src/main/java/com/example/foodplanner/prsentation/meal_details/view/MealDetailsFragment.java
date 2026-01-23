package com.example.foodplanner.prsentation.meal_details.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.prsentation.meal_details.presenter.MealDetailsPresenter;
import com.example.foodplanner.prsentation.meal_details.presenter.MealDetailsPresenterImp;
import com.google.android.material.card.MaterialCardView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class MealDetailsFragment extends Fragment implements MealDetailsView {

    private MealDetailsPresenter presenter;
    private String mealId;

    private ImageView ivMealImage;
    private TextView tvMealName, tvMealAlternate, badgeCategory, badgeArea;
    private TextView tvMealInstructions, tvYoutubeTitle, tv_country;
    private RecyclerView rvIngredients;
    private MaterialCardView cardYoutube;
    private YouTubePlayerView youtubePlayerView;
    private Button btnYoutube;

    private IngredientsAdapter ingredientsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MealDetailsPresenterImp(requireContext(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_details, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mealId = bundle.getString("idMeal");
            Log.d("MealDetailsFragment", "Received mealId: " + mealId);
        }

        ivMealImage = view.findViewById(R.id.iv_meal_image);
        tvMealName = view.findViewById(R.id.tv_meal_name);
        tvMealAlternate = view.findViewById(R.id.tv_meal_alternate);
        badgeCategory = view.findViewById(R.id.badge_category);
        badgeArea = view.findViewById(R.id.badge_area);
        rvIngredients = view.findViewById(R.id.rv_ingredients);
        tvMealInstructions = view.findViewById(R.id.tv_meal_instructions);
        tv_country = view.findViewById(R.id.tv_country);
        tvYoutubeTitle = view.findViewById(R.id.tv_youtube_title);
        cardYoutube = view.findViewById(R.id.card_youtube);
        youtubePlayerView = view.findViewById(R.id.youtube_player_view);
        btnYoutube = view.findViewById(R.id.btn_youtube);

        ingredientsAdapter = new IngredientsAdapter(new ArrayList<>());
        rvIngredients.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvIngredients.setAdapter(ingredientsAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mealId != null && !mealId.isEmpty()) {
            presenter.getMealDetailsById(mealId);
        }
    }

    @Override
    public void onMealDetailsFetchSuccess(List<Meal> meals) {
        if (meals == null || meals.isEmpty()) {
            onMealDetailsFetchError("No meal details found");
            return;
        }

        Meal meal = meals.get(0);

        Glide.with(this)
                .load(meal.getStrMealThumb())
                .placeholder(R.drawable.placeholder)
                .into(ivMealImage);

        tvMealName.setText(meal.getStrMeal());

        if (meal.getStrMealAlternate() != null && !meal.getStrMealAlternate().isEmpty()) {
            tvMealAlternate.setText(meal.getStrMealAlternate());
            tvMealAlternate.setVisibility(View.VISIBLE);
        }

        badgeCategory.setText(meal.getStrCategory());
        badgeArea.setText(meal.getStrArea());
        tvMealInstructions.setText(meal.getStrInstructions());
        tv_country.setText(meal.getStrArea());

        List<String> ingredients = new ArrayList<>();
        List<String> measures = new ArrayList<>();

        addIngredient(ingredients, measures, meal.getStrIngredient1(), meal.getStrMeasure1());
        addIngredient(ingredients, measures, meal.getStrIngredient2(), meal.getStrMeasure2());
        addIngredient(ingredients, measures, meal.getStrIngredient3(), meal.getStrMeasure3());
        addIngredient(ingredients, measures, meal.getStrIngredient4(), meal.getStrMeasure4());
        addIngredient(ingredients, measures, meal.getStrIngredient5(), meal.getStrMeasure5());
        addIngredient(ingredients, measures, meal.getStrIngredient6(), meal.getStrMeasure6());
        addIngredient(ingredients, measures, meal.getStrIngredient7(), meal.getStrMeasure7());
        addIngredient(ingredients, measures, meal.getStrIngredient8(), meal.getStrMeasure8());
        addIngredient(ingredients, measures, meal.getStrIngredient9(), meal.getStrMeasure9());
        addIngredient(ingredients, measures, meal.getStrIngredient10(), meal.getStrMeasure10());
        addIngredient(ingredients, measures, meal.getStrIngredient11(), meal.getStrMeasure11());
        addIngredient(ingredients, measures, meal.getStrIngredient12(), meal.getStrMeasure12());
        addIngredient(ingredients, measures, meal.getStrIngredient13(), meal.getStrMeasure13());
        addIngredient(ingredients, measures, meal.getStrIngredient14(), meal.getStrMeasure14());
        addIngredient(ingredients, measures, meal.getStrIngredient15(), meal.getStrMeasure15());
        addIngredient(ingredients, measures, meal.getStrIngredient16(), meal.getStrMeasure16());
        addIngredient(ingredients, measures, meal.getStrIngredient17(), meal.getStrMeasure17());
        addIngredient(ingredients, measures, meal.getStrIngredient18(), meal.getStrMeasure18());
        addIngredient(ingredients, measures, meal.getStrIngredient19(), meal.getStrMeasure19());
        addIngredient(ingredients, measures, meal.getStrIngredient20(), meal.getStrMeasure20());

        ingredientsAdapter.setIngredients(ingredients, measures);

        String youtubeUrl = meal.getStrYoutube();
        if (youtubeUrl != null && !youtubeUrl.isEmpty()) {
            String videoId = extractYouTubeVideoId(youtubeUrl);
            if (videoId != null) {
                tvYoutubeTitle.setVisibility(View.VISIBLE);
                cardYoutube.setVisibility(View.VISIBLE);
                btnYoutube.setVisibility(View.VISIBLE);

                getLifecycle().addObserver(youtubePlayerView);

                youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        youTubePlayer.cueVideo(videoId, 0);
                        btnYoutube.setOnClickListener(v -> youTubePlayer.play());
                    }
                });
            }
        }
    }

    @Override
    public void onMealDetailsFetchError(String errorMsg) {
        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMealDetailsFetchLoading() {
    }

    private void addIngredient(List<String> ingredients, List<String> measures,
                               String ingredient, String measure) {
        if (ingredient != null && !ingredient.trim().isEmpty()) {
            ingredients.add(ingredient);
            measures.add(measure != null ? measure : "");
        }
    }

    private String extractYouTubeVideoId(String url) {
        if (url.contains("v=")) {
            return url.split("v=")[1].split("&")[0];
        }
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (youtubePlayerView != null) {
            getLifecycle().removeObserver(youtubePlayerView);
        }
    }
}
