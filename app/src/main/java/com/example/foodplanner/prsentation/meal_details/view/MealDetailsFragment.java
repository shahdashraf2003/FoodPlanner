package com.example.foodplanner.prsentation.meal_details.view;

import static com.example.foodplanner.utils.CalenderUtil.showCalendarDialog;
import static com.example.foodplanner.utils.SnackBarUtil.showSnack;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.data.auth.datasource.local.SessionManager;

import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.prsentation.meal_details.presenter.MealDetailsPresenter;
import com.example.foodplanner.prsentation.meal_details.presenter.MealDetailsPresenterImp;
import com.example.foodplanner.utils.NetworkConnectionObserver;
import com.example.foodplanner.utils.NoInternetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class MealDetailsFragment extends Fragment implements MealDetailsView ,MealOnClickListener{

    private MealDetailsPresenter presenter;
    private String mealId;

   private ImageView ivMealImage;
    private TextView tvMealName, badgeCategory, badgeArea, tvMealInstructions, tvYoutubeTitle;
    private RecyclerView rvIngredients;
    private MaterialCardView cardYoutube;
    private YouTubePlayerView youtubePlayerView;
    private Button btnYoutube;
    private ProgressBar progressLoading;
    private TextView tvError;
    private Meal meal;

private NetworkConnectionObserver networkObserver;
    private IngredientsAdapter ingredientsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MealDetailsPresenterImp(this, requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_details, container, false);
        SessionManager sessionManager = new SessionManager(requireContext());
        boolean isGuest = sessionManager.isGuest();
        Bundle bundle = getArguments();
        if (bundle != null) {
            mealId = bundle.getString("idMeal");
        }

        ivMealImage = view.findViewById(R.id.iv_meal_image);
        tvMealName = view.findViewById(R.id.tv_meal_name);
        badgeCategory = view.findViewById(R.id.badge_category);
        badgeArea = view.findViewById(R.id.badge_area);
        rvIngredients = view.findViewById(R.id.rv_ingredients);
        tvMealInstructions = view.findViewById(R.id.tv_meal_instructions);
        tvYoutubeTitle = view.findViewById(R.id.tv_youtube_title);
        cardYoutube = view.findViewById(R.id.card_youtube);
        youtubePlayerView = view.findViewById(R.id.youtube_player_view);
        btnYoutube = view.findViewById(R.id.btn_youtube);
        progressLoading = view.findViewById(R.id.progressMealDetails);
        tvError = view.findViewById(R.id.tvErrorMealDetails);
        ingredientsAdapter = new IngredientsAdapter(new ArrayList<>(), new ArrayList<>());
        rvIngredients.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvIngredients.setAdapter(ingredientsAdapter);
        FloatingActionButton fab = view.findViewById(R.id.fab_favorite);



        fab.setOnClickListener(v -> {
            Log.d("favorite", "onCreateView: " + meal);
            if (isGuest) {
                showGuestWarningDialog();
            } else {
                if (meal != null) {
                    addMealToFav(meal);
                }
            }
        });

         FloatingActionButton addToCal = view.findViewById(R.id.fab_calender);
            addToCal.setOnClickListener(v -> {
                if (isGuest) {
                    showGuestWarningDialog();
                } else {
                    if (meal != null) {
                        showCalendarDialog(meal, requireContext(), date -> {
                            presenter.addMealToCalendar(meal, date);
                        });
                    }
                }
            });

        NoInternetDialog noInternetDialog = new NoInternetDialog(requireContext());


         networkObserver = new NetworkConnectionObserver(requireContext(), new NetworkConnectionObserver.NetworkListener() {
            @Override
            public void onNetworkLost() {
                Log.d("No", "onNetworkLost: ");
                System.out.println("onNetworkLost");
                requireActivity().runOnUiThread(() -> noInternetDialog.showDialog());

            }

            @Override
            public void onNetworkAvailable() {
                requireActivity().runOnUiThread(() -> noInternetDialog.hideDialog());
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mealId != null && !mealId.isEmpty()) {
            presenter.getMealDetailsById(mealId);
        } else {
            showError("Meal ID is missing");
        }
    }

    private void showLoading(boolean isLoading) {
        progressLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        rvIngredients.setVisibility(isLoading ? View.INVISIBLE : View.VISIBLE);
        ivMealImage.setVisibility(isLoading ? View.INVISIBLE : View.VISIBLE);
        tvError.setVisibility(View.GONE);
        tvMealName.setVisibility(isLoading ? View.INVISIBLE : View.VISIBLE);
        tvMealInstructions.setVisibility(isLoading ? View.INVISIBLE : View.VISIBLE);
    }

    private void showError(String message) {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(message);
        progressLoading.setVisibility(View.GONE);
        ivMealImage.setVisibility(View.GONE);
        rvIngredients.setVisibility(View.GONE);
        tvMealName.setVisibility(View.GONE);
        tvMealInstructions.setVisibility(View.GONE);
        cardYoutube.setVisibility(View.GONE);
        btnYoutube.setVisibility(View.GONE);
        tvYoutubeTitle.setVisibility(View.GONE);
    }

    @Override
    public void onMealDetailsFetchLoading() {
        showLoading(true);
    }

    @Override
    public void onMealDetailsFetchError(String errorMsg) {
        showError(errorMsg);
    }

    @Override
    public void onMealDetailsFetchSuccess(List<Meal> meals) {
        if (meals == null || meals.isEmpty()) {
            showError("No meal details found");
            return;
        }

        showLoading(false);

         meal = meals.get(0);

        Glide.with(this)
                .load(meal.getStrMealThumb())
                .placeholder(R.drawable.placeholder)
                .into(ivMealImage);

        tvMealName.setText(meal.getStrMeal());



        badgeCategory.setText(meal.getStrCategory());
        badgeArea.setText(meal.getStrArea());
        tvMealInstructions.setText(meal.getStrInstructions());

        List<String> ingredients = new ArrayList<>();
        List<String> measures = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            try {
                String ingredient = (String) Meal.class.getMethod("getStrIngredient" + i).invoke(meal);
                String measure = (String) Meal.class.getMethod("getStrMeasure" + i).invoke(meal);
                if (ingredient != null && !ingredient.trim().isEmpty()) {
                    ingredients.add(ingredient);
                    measures.add(measure != null ? measure : "");
                }
            } catch (Exception ignored) {}
        }

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
    public void showMessage(String message) {
        showSnack(requireView().getRootView(), message);
    }




    private String extractYouTubeVideoId(String url) {
        if (url.contains("v=")) return url.split("v=")[1].split("&")[0];
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (youtubePlayerView != null) getLifecycle().removeObserver(youtubePlayerView);
    }


    @Override
    public void addMealToFav(Meal meal) {
        presenter.insertMealToFav(meal);

    }
    private void showGuestWarningDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_guest_warning, null);
        builder.setView(dialogView);

        androidx.appcompat.app.AlertDialog dialog = builder.create();

        dialogView.findViewById(R.id.btn_guest_ok).setOnClickListener(v -> dialog.dismiss());

        dialog.setCancelable(false);
        dialog.show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (networkObserver != null) {
            networkObserver.unregister();
        }
    }

}
