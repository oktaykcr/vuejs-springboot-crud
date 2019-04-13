<template>
  <div>
    <h2>User Edit</h2>
    <div id="form">
      <p>UserId : {{ user.id }}</p>
      <div class="form-row">
        <div class="col">
          <input
            type="text"
            class="form-control"
            :class="{'error-input': !$v.user.firstname.required || !$v.user.firstname.minLength}"
            placeholder="First name"
            v-model.trim="$v.user.firstname.$model"
          >
          <p class="error" v-if="!$v.user.firstname.required">Firstname is required.</p>
          <p
            class="error"
            v-if="!$v.user.firstname.minLength"
          >Firstname must have at least {{$v.user.firstname.$params.minLength.min}} letters.</p>
        </div>
        <div class="col">
          <input
            type="text"
            class="form-control"
            :class="{'error-input': !$v.user.lastname.required || !$v.user.lastname.minLength}"
            placeholder="Last name"
            v-model.trim="$v.user.lastname.$model"
          >
          <p class="error" v-if="!$v.user.lastname.required">Lastname is required.</p>
          <p
            class="error"
            v-if="!$v.user.lastname.minLength"
          >Lastname must have at least {{$v.user.lastname.$params.minLength.min}} letters.</p>
        </div>
        <div class="col">
          <input
            type="number"
            class="form-control"
            :class="{'error-input': !$v.user.age.required || !$v.user.age.minValue}"
            placeholder="Age"
            v-model.trim="$v.user.age.$model"
          >
          <p class="error" v-if="!$v.user.age.required">Age is required.</p>
          <p
            class="error"
            v-if="!$v.user.age.minValue"
          >Age must be at least {{ $v.user.age.$params.minValue.min }} value.</p>
        </div>
      </div>
      <br>
      <button class="btn btn-primary" @click="editUser">Edit</button>
    </div>
  </div>
</template>


<script>
import { EventBus } from "./../../event-bus";
import { required, minLength, minValue } from "vuelidate/lib/validators";

export default {
  name: "UserEdit",
  data() {
    return {
      errors: [],
      user: {
        id: this.$route.params.id,
        firstname: "",
        lastname: "",
        age: 0
      }
    };
  },
  validations: {
    user: {
      firstname: {
        required,
        minLength: minLength(3)
      },
      lastname: {
        required,
        minLength: minLength(3)
      },
      age: {
        required,
        minValue: minValue(0)
      }
    }
  },
  methods: {
    editUser() {
      this.$http.put("update", this.user).then(
        response => {
          EventBus.$emit("userIsUpdated", response.body);
        },
        error => {
          EventBus.$emit("userIsUpdated", error.body.errorCode);
        }
      );
    }
  },
  beforeRouteUpdate(to, from, next) {
    // get user from api server according to params id
    this.$http.get("get?id=" + to.params.id).then(response => {
      this.user = response.body;
    });
    next();
  },
  created() {
    this.$http.get("get?id=" + this.$route.params.id).then(response => {
      this.user = response.body;
    });
  }
};
</script>

