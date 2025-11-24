package com.example.baitapso9

// MainActivity.kt
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var mainRecyclerView: RecyclerView
    private lateinit var tabLayout: TabLayout
    private lateinit var mainAdapter: MainAdapter
    private val mainItems = mutableListOf<MainListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupTabLayout()
        setupRecyclerView()
        loadData()
    }

    private fun setupTabLayout() {
        tabLayout = findViewById(R.id.tabLayout)

        // Set first tab as selected by default
        tabLayout.getTabAt(0)?.select()
    }

    private fun setupRecyclerView() {
        mainRecyclerView = findViewById(R.id.mainRecyclerView)
        mainRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        mainAdapter = MainAdapter(mainItems) { app ->
            // Handle app click
            Toast.makeText(this, "Clicked on ${app.title}", Toast.LENGTH_SHORT).show()
        }

        mainRecyclerView.adapter = mainAdapter
    }

    private fun loadData() {
        // Add "Sponsored" section header
        mainItems.add(MainListItem.SectionHeaderItem("Sponsored - Suggested for you"))

        // Add sponsored apps (vertical list)
        val sponsoredApps = listOf(
            StoreItem(
                id = "1",
                title = "Mech Assemble: Zombie Swarm",
                subtitle = null,
                iconUrl = "",
                genres = listOf("Action", "Role Playing", "Roguelike", "Zombie"),
                rating = 4.8,
                size = "624 MB",
                displayType = DisplayType.VERTICAL_LIST
            ),
            StoreItem(
                id = "2",
                title = "Mobile Legends: Bang Bang",
                subtitle = null,
                iconUrl = "",
                genres = listOf("Action", "MOBA", "Strategy"),
                rating = 4.6,
                size = "1.2 GB",
                displayType = DisplayType.VERTICAL_LIST
            ),
            StoreItem(
                id = "3",
                title = "Genshin Impact",
                subtitle = null,
                iconUrl = "",
                genres = listOf("Action", "RPG", "Open World"),
                rating = 4.7,
                size = "8.5 GB",
                displayType = DisplayType.VERTICAL_LIST
            )
        )

        mainItems.add(
            MainListItem.AppListSectionItem(
                sectionTitle = "Sponsored - Suggested for you",
                apps = sponsoredApps,
                isHorizontal = false
            )
        )

        // Add divider
        mainItems.add(MainListItem.DividerItem)

        // Add "Recommended for you" section header
        mainItems.add(MainListItem.SectionHeaderItem("Recommended for you"))

        // Add recommended apps (horizontal list)
        val recommendedApps = listOf(
            StoreItem(
                id = "4",
                title = "Suno - AI Music & Game",
                subtitle = "Claude by",
                iconUrl = "",
                genres = null,
                rating = 4.5,
                size = "45 MB",
                displayType = DisplayType.HORIZONTAL_CARD
            ),
            StoreItem(
                id = "5",
                title = "DramaBox",
                subtitle = "DramaBox -",
                iconUrl = "",
                genres = null,
                rating = 4.4,
                size = "28 MB",
                displayType = DisplayType.HORIZONTAL_CARD
            ),
            StoreItem(
                id = "6",
                title = "ChatGPT",
                subtitle = "OpenAI",
                iconUrl = "",
                genres = null,
                rating = 4.6,
                size = "32 MB",
                displayType = DisplayType.HORIZONTAL_CARD
            ),
            StoreItem(
                id = "7",
                title = "Spotify",
                subtitle = "Spotify AB",
                iconUrl = "",
                genres = null,
                rating = 4.3,
                size = "18 MB",
                displayType = DisplayType.HORIZONTAL_CARD
            ),
            StoreItem(
                id = "8",
                title = "Netflix",
                subtitle = "Netflix, Inc.",
                iconUrl = "",
                genres = null,
                rating = 4.2,
                size = "24 MB",
                displayType = DisplayType.HORIZONTAL_CARD
            )
        )

        mainItems.add(
            MainListItem.AppListSectionItem(
                sectionTitle = "Recommended for you",
                apps = recommendedApps,
                isHorizontal = true
            )
        )

        // Add another section - "Top Charts"
        mainItems.add(MainListItem.DividerItem)
        mainItems.add(MainListItem.SectionHeaderItem("Top Charts"))

        val topChartsApps = listOf(
            StoreItem(
                id = "9",
                title = "Clash of Clans",
                subtitle = null,
                iconUrl = "",
                genres = listOf("Strategy", "Tower Defense"),
                rating = 4.9,
                size = "150 MB",
                displayType = DisplayType.VERTICAL_LIST
            ),
            StoreItem(
                id = "10",
                title = "Candy Crush",
                subtitle = null,
                iconUrl = "",
                genres = listOf("Puzzle", "Match 3"),
                rating = 4.5,
                size = "85 MB",
                displayType = DisplayType.VERTICAL_LIST
            )
        )

        mainItems.add(
            MainListItem.AppListSectionItem(
                sectionTitle = "Top Charts",
                apps = topChartsApps,
                isHorizontal = false
            )
        )

        mainAdapter.notifyDataSetChanged()
    }
}