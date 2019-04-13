<template>
  <div>
    <div class="alert alert-danger alert-dismissible fade show" role="alert" v-if="alert.isOpen">
      {{ alert.msg }}
      <button type="button" class="close" @click="alert.isOpen = false">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <p>Total User : {{ total }}</p>
    <table class="table">
      <thead>
        <tr>
          <th scope="col">Id</th>
          <th scope="col">Firstname</th>
          <th scope="col">Lastname</th>
          <th scope="col">Age</th>
          <th scope="col">Action</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in users" :key="user.id">
          <th scope="row">{{ user.id }}</th>
          <td>{{ user.firstname }}</td>
          <td>{{ user.lastname }}</td>
          <td>{{ user.age }}</td>
          <td>
            <button
              id="action-button"
              title="Edit"
              class="btn btn-warning"
              role="button"
              @click="editUser(user.id)"
            >
              <i class="fa fa-edit"></i>
            </button>
            <button
              id="action-button"
              title="Remove"
              class="btn btn-danger"
              role="button"
              @click="removeUser(user.id)"
            >
              <i class="fa fa-trash-alt"></i>
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>


<script>
import { EventBus } from "./../../event-bus";

export default {
  name: "Users",
  data() {
    return {
      alert: {
        isOpen: false,
        msg: ""
      },
      users: [],
      total: 0
    };
  },
  methods: {
    editUser(id) {
      this.$router.push("/" + id + "/edit");
    },
    removeUser(id) {
      this.$http.delete("delete?id=" + id).then(
        response => {
          console.log(response);
          this.users = this.users.filter(value => {
            return value.id !== id;
          });
          this.total--;
        },
        () => {
          this.alert.isOpen = true;
          this.alert.msg = "User couldn't delete!";
        }
      );
    }
  },
  created() {
    this.$http.get("list").then(
      response => {
        this.users = response.body.data;
        this.total = response.data.totalCount;
      },
      () => {
        this.alert.isOpen = true;
        this.alert.msg = "Users couldn't fetch from server!";
      }
    );

    EventBus.$on("userIsCreated", data => {
      if (data) {
        /* if the data has id field so can be push into users array
      otherwise there is an error occur*/
        if (data.id) {
          this.users.push(data);
          this.total++;
          if (this.alert.isOpen) this.alert.isOpen = false;
        } else {
          this.alert.isOpen = true;
          this.alert.msg = "The User couldn't create! " + data;
        }
      } else {
        this.alert.isOpen = true;
        this.alert.msg = "Couldn't get any response from the server!";
      }
    });

    EventBus.$on("userIsUpdated", data => {
      if (data.id) {
        /* find the updated user according to id 
        and update the user in the users array */
        var userIndex = this.users.findIndex(user => user.id == data.id);
        if (userIndex != -1) this.$set(this.users, userIndex, data);
      } else {
        this.alert.isOpen = true;
        this.alert.msg = "The User couldn't update! " + data;
      }
    });
  }
};
</script>

<style scoped>
#action-button {
  margin: 0px 2% 0px 2%;
}
</style>


