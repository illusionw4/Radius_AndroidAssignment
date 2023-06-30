import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.asthana.radius.Models.ApiModels
import com.asthana.radius.R

class Adapter(
    private val context: Context,
    private var facilities: List<ApiModels.Facility>,
    private val exclusions: List<List<ApiModels.Exclusion>>
) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private val selectedOptionsMap: MutableMap<String, ApiModels.Option> = mutableMapOf()

    fun setData(data: List<ApiModels.Facility>) {
        facilities = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val facility = facilities[position]
        val optionsLayout = holder.optionsLayout
        optionsLayout.removeAllViews()

        holder.facilityNameTextView.text = facility.name

        // Populate Facility Options
        for (option in facility.options) {
            val optionView = LayoutInflater.from(optionsLayout.context).inflate(R.layout.item_option, optionsLayout, false)
            val optionImageView = optionView.findViewById<ImageView>(R.id.optionImageView)
            val optionTextView = optionView.findViewById<TextView>(R.id.optionTextView)

            optionTextView.text = option.name
            // Set icon based on icon name
            val iconName = option.icon
            val iconResId = getIconResourceId(context, iconName)
            optionImageView.setImageResource(iconResId)

            optionsLayout.addView(optionView)

            // Check if the option is selected
            val isSelected = option == selectedOptionsMap[facility.facility_id]

            // Set the background tint color based on the selected state
            if (isSelected) {
                optionView.backgroundTintList = ContextCompat.getColorStateList(context, R.color.white)
            } else {
//                optionView.backgroundTintList = ContextCompat.getColorStateList(context, android.R.color.transparent)
            }


            // Handle option click event
            optionView.setOnClickListener {
                // Select the clicked option
                selectedOptionsMap[facility.facility_id] = option

                // Check for exclusions
                val hasExclusions = checkExclusions(selectedOptionsMap, exclusions)

                if (hasExclusions) {
                    // Handle exclusion combination, e.g., show an error message
                    Toast.makeText(context, "Exclusion combination not allowed", Toast.LENGTH_SHORT).show()

                    // Deselect the current option
                    selectedOptionsMap.remove(facility.facility_id)
                } else {
                    // Update the UI based on exclusions, e.g., hide error message
                    // ...

                    // Notify the adapter that the data has changed to update the item views
                    notifyDataSetChanged()
                }
            }
        }
    }



    private fun getIconResourceId(context: Context, iconName: String): Int {
        val modifiedIconName = iconName.replace("-", "_") // Replace '-' with '_' in the icon name
        val resources = context.resources
        val packageName = context.packageName
        return resources.getIdentifier(modifiedIconName, "drawable", packageName)
    }

    private fun checkExclusions(
        selectedOptionsMap: Map<String, ApiModels.Option>,
        exclusions: List<List<ApiModels.Exclusion>>
    ): Boolean {
        for (exclusionList in exclusions) {
            val exclusion1 = exclusionList[0]
            val exclusion2 = exclusionList[1]

            val option1 = selectedOptionsMap[exclusion1.facility_id]
            val option2 = selectedOptionsMap[exclusion2.facility_id]

            if (option1 != null && option2 != null &&
                option1.id == exclusion1.options_id && option2.id == exclusion2.options_id
            ) {
                // Exclusion combination found
                return true
            }
        }

        // No exclusion combination found
        return false
    }

    override fun getItemCount(): Int {
        return facilities.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val facilityNameTextView: TextView = itemView.findViewById(R.id.facilityNameTextView)
        val optionsLayout: LinearLayout = itemView.findViewById(R.id.optionsLayout)
    }
}
